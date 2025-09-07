package com.pesupal.server.service.implementations.post;

import com.pesupal.server.dto.request.post.CreatePostDto;
import com.pesupal.server.dto.request.post.CreatePostMentionsDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.post.*;
import com.pesupal.server.enums.OrgAction;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.MandatoryDataMissingException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.factory.TrendingPostsAnalyserFactory;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.DateTimeUtil;
import com.pesupal.server.model.post.*;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.post.PostRepository;
import com.pesupal.server.service.interfaces.MediaService;
import com.pesupal.server.service.interfaces.org.OrgConfigurationService;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import com.pesupal.server.service.interfaces.post.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl extends CurrentValueRetriever implements PostService {

    private final PollService pollService;
    private final MediaService mediaService;
    private final PostRepository postRepository;
    private final PostTagService postTagService;
    private final BookmarkService bookmarkService;
    private final OrgMemberService orgMemberService;
    private final PostMediaService postMediaService;
    private final PostMentionService postMentionService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final OrgConfigurationService orgConfigurationService;
    private final TrendingPostsAnalyserFactory trendingPostsAnalyserFactory;

    private final static int MAXIMUM_TAGS_PER_POST = 10;
    private final static String SCHEDULED_POST_KEY = "scheduled:posts";
    private final static String TRENDING_POSTS_KEY = "trending:posts";
    private final static Duration TRENDING_POSTS_CACHE_DURATION = Duration.ofHours(6);
    private final static String TRENDING_POSTS_ANALYSER_ALGORITHM = "ENGAGEMENT_BASED";

    /**
     * To validate the CreatePostDto before performing any operations.
     *
     * @param createPostDto
     */
    private void validateCreatePostDto(CreatePostDto createPostDto, OrgMember creator) {

        if (createPostDto.getTags() != null && createPostDto.getTags().size() > MAXIMUM_TAGS_PER_POST) {
            throw new ActionProhibitedException("A post can have a maximum of " + MAXIMUM_TAGS_PER_POST + " tags.");
        }

        CreatePostMentionsDto createPostMentionsDto = createPostDto.getMentions();
        if (createPostMentionsDto != null && createPostMentionsDto.getLabel() != null) {
            String label = createPostMentionsDto.getLabel().trim();
            Set<String> data = createPostMentionsDto.getData();
            if (!data.isEmpty() && label.isEmpty()) {
                throw new MandatoryDataMissingException("Specify a label for the mention.");
            }
            if (data.stream().anyMatch(id -> id.equals(creator.getPublicId()))) {
                throw new ActionProhibitedException("You cannot mention yourself in a post.");
            }
        }
    }

    /**
     * Creates a new post - Internal use only.
     *
     * @param createPostDto
     */
    @Transactional
    public Post createPostInternal(CreatePostDto createPostDto) {

        OrgMember creator = getCurrentOrgMember();

        if (!orgConfigurationService.hasPrivilegeTo(OrgAction.CREATE_POST, creator.getRole())) {
            throw new PermissionDeniedException("You don't have permission to create post.");
        }

        if (!createPostDto.getMediaIds().isEmpty() && orgConfigurationService.hasPrivilegeTo(OrgAction.ATTACH_MEDIA_IN_POST, creator.getRole())) {
            throw new PermissionDeniedException("You don't have permission to attach media in post.");
        }

        validateCreatePostDto(createPostDto, creator);

        boolean hasPoll = createPostDto.getPoll() != null;
        Post post = createPostDto.toPost();
        post.setOrg(creator.getOrg());
        post.setCreator(creator);
        post.setHasPoll(hasPoll);
        postRepository.save(post);
        List<PostMedia> postMedia = postMediaService.saveAll(createPostDto.getMediaIds(), post);
        List<PostTag> postTags = postTagService.saveAll(createPostDto.getTags(), post);
        List<PostMention> postMentions = postMentionService.saveAll(createPostDto.getMentions(), post);
        if (hasPoll) {
            pollService.createPoll(createPostDto.getPoll(), post);
        }
        post.setTags(postTags);
        post.setPostMedia(postMedia);
        post.setMentions(postMentions);
        return post;
    }

    /**
     * Creates a new post - External use.
     *
     * @param createPostDto
     * @return
     */
    @Override
    public PostDto createPost(CreatePostDto createPostDto) {

        OrgMember orgMember = getCurrentOrgMember();

        Post post = createPostInternal(createPostDto);
        return getPostDtoFromPostAndOrgMember(post, orgMember);
    }

    /**
     * Schedules a post for future publication.
     *
     * @param createPostDto
     * @return
     */
    @Override
    public PostDto schedulePost(CreatePostDto createPostDto) {

        if (createPostDto.getScheduledAt().isBefore(LocalDateTime.now())) {
            throw new ActionProhibitedException("Scheduled time must be in the future.");
        }

        OrgMember orgMember = getCurrentOrgMember();

        Post post = createPostInternal(createPostDto);
        long currentTimeMillis = DateTimeUtil.toEpochMilli(createPostDto.getScheduledAt());
        redisTemplate.opsForZSet().add(SCHEDULED_POST_KEY, post.getId(), currentTimeMillis);
        return getPostDtoFromPostAndOrgMember(post, orgMember);
    }

    /**
     * Unschedules a scheduled post - Internal use only.
     *
     * @param post
     * @return
     */
    private Post unschedulePostInternal(Post post) {

        if (!post.getStatus().equals(PostStatus.SCHEDULED)) {
            throw new ActionProhibitedException("Post is not scheduled.");
        }

        post.setCreatedAt(LocalDateTime.now());
        post.setStatus(PostStatus.PUBLISHED);
        return postRepository.save(post);
    }

    /**
     * Unschedules a scheduled post - Internal use only.
     *
     * @param postId
     * @return
     */
    private void unschedulePost(Long postId) {

        Post post = getPostById(postId);
        unschedulePostInternal(post);
    }

    /**
     * Unschedules a scheduled post - External use.
     *
     * @param postId
     * @param triggeredBy
     * @return
     */
    @Override
    public void unschedulePost(String postId, OrgMember triggeredBy) {

        Post post = getPostByPublicId(postId);

        if (!post.getCreator().getPublicId().equals(triggeredBy.getPublicId())) {
            throw new PermissionDeniedException("You do not have permission to perform this action.");
        }

        unschedulePostInternal(post);
    }

    /**
     * Publishes a scheduled post immediately.
     */
    @Scheduled(cron = "0 * * * * *")
    public void publishScheduledPost() {

        long currentTimeMillis = DateTimeUtil.toEpochMilli(LocalDateTime.now());
        Set<Object> postIds = redisTemplate.opsForZSet().rangeByScore(SCHEDULED_POST_KEY, 0, currentTimeMillis);

        for (Object postId : Objects.requireNonNull(postIds)) {
            try {
                redisTemplate.opsForZSet().remove(SCHEDULED_POST_KEY, postId);
                Long id = Long.parseLong(postId.toString());
                unschedulePost(id);
                // TODO: Send Post Published Notification in Bot
            } catch (Exception ignored) {
            }
        }
    }

    private boolean isLiked(List<PostLike> likes, User user) {

        return likes.stream().anyMatch(like -> Objects.equals(like.getLiker().getId(), user.getId()));
    }

    /**
     * Get unique mentions from the list of mentions
     *
     * @param mentions
     * @return
     */
    private List<UserPreviewDto> getUniqueMentions(List<PostMention> mentions) {

        Set<String> uniqueMemberIds = new HashSet<>();
        List<UserPreviewDto> userPreviews = new ArrayList<>();
        for (PostMention mention : mentions) {
            OrgMember member = mention.getMentionedMember();
            String memberId = member.getPublicId();
            if (uniqueMemberIds.contains(memberId)) {
                continue;
            }
            uniqueMemberIds.add(memberId);
            userPreviews.add(orgMemberService.getUserPreview(member));
        }
        return userPreviews;
    }

    /**
     * Converts a Post entity and OrgMember to a PostDto.
     *
     * @param post
     * @param orgMember
     * @return PostDto
     */
    @Override
    public PostDto getPostDtoFromPostAndOrgMember(Post post, OrgMember orgMember) {

        PostDto postDto = PostDto.fromPost(post);
        postDto.setTags(post.getTags().stream().map(postTag -> postTag.getTag().getName()).sorted().toList());
        postDto.setMedia(post.getPostMedia().stream().map(postMedia -> {
            PostMediaDto postMediaDto = PostMediaDto.fromPostMedia(postMedia);
            postMediaDto.setUrl(mediaService.generatePresignedUrl(postMedia.getMediaId()));
            return postMediaDto;
        }).toList());
        postDto.setOwner(orgMemberService.getUserBasicInfo(post.getCreator()));
        postDto.setImpression(PostImpressionDto.builder().likes(post.getLikes().size()).comments(post.getComments().size()).build());
        postDto.setBookmarked(false);   // Feature not implemented yet
        if (post.isHasPoll()) {
            postDto.setPoll(PollDto.fromPoll(pollService.getPollByPost(post), orgMember.getId()));
        }
        if (post.getPostMentionLabel() != null) {
            postDto.setMentions(new PostMentionsDto(post.getPostMentionLabel(), getUniqueMentions(post.getMentions())));
        }
        postDto.setCreator(orgMember.getId().equals(post.getCreator().getId()));
        postDto.setLiked(isLiked(post.getLikes(), orgMember.getUser()));
        postDto.setBookmarked(bookmarkService.isBookmarked(post, orgMember));
        return postDto;
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param postId
     * @param orgId
     * @return Post
     */
    @Override
    public Post getPostByIdAndOrgId(Long postId, Long orgId) {

        return postRepository.findByIdAndOrgId(postId, orgId).orElseThrow(() -> new DataNotFoundException("Post does not exist."));
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param postId
     * @return PostDto
     */
    @Override
    public PostDto getPostByIdAndOrgId(String postId) {

        OrgMember orgMember = getCurrentOrgMember();
        Long orgId = orgMember.getOrg().getId();
        Post post = getPostByPublicIdAndOrgId(postId, orgId);
        return getPostDtoFromPostAndOrgMember(post, orgMember);
    }

    /**
     * Retrieves a post by its public ID and organization ID.
     *
     * @param postId
     * @param orgId
     * @return
     */
    @Override
    public Post getPostByPublicIdAndOrgId(String postId, Long orgId) {

        return postRepository.findByPublicIdAndOrgId(postId, orgId).orElseThrow(() -> new DataNotFoundException("Post does not exist."));
    }

    /**
     * Retrieves a list of posts by user ID and organization ID.
     *
     * @return
     */
    @Override
    public PostsListDto getPostByUserId(String creatorId, int page, int size, SortOrder sortOrder) {

        OrgMember orgMember = getCurrentOrgMember();
        Long orgId = orgMember.getOrg().getId();

        Sort sort = Sort.by(sortOrder == SortOrder.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage = postRepository.findAllByOrgIdAndCreator_PublicIdAndStatus(orgId, creatorId, pageable, PostStatus.PUBLISHED);

        List<PostDto> postDtos = new ArrayList<>(postPage.getContent().stream().map(post -> getPostDtoFromPostAndOrgMember(post, orgMember)).toList());
        PostsListDto postsListDto = new PostsListDto();
        postsListDto.setInfo(Map.of(
                "hasMoreRecords", postPage.hasNext()
        ));
        postsListDto.setPosts(postDtos);
        return postsListDto;
    }

    /**
     * Get scheduled posts
     *
     * @param page
     * @param size
     * @param sortOrder
     * @return
     */
    @Override
    public PostsListDto getScheduledPosts(int page, int size, SortOrder sortOrder) {

        OrgMember orgMember = getCurrentOrgMember();

        Sort sort = Sort.by(sortOrder == SortOrder.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> posts = postRepository.findAllByCreatorAndStatusAndCreatedAtAfter(orgMember, PostStatus.SCHEDULED, LocalDateTime.now(), pageable);
        List<PostDto> postDtos = new ArrayList<>(posts.getContent().stream().map(post -> getPostDtoFromPostAndOrgMember(post, orgMember)).toList());

        PostsListDto postsListDto = new PostsListDto();
        postsListDto.setInfo(Map.of("hasMoreRecords", posts.hasNext()));
        postsListDto.setPosts(postDtos);
        return postsListDto;
    }

    /**
     * Retrieves trending posts.
     *
     * @param limit
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<PostDto> getTrendingPosts(int limit) {

        OrgMember orgMember = getCurrentOrgMember();

        // Fetch post ids from redis.
        String key = TRENDING_POSTS_KEY + ":{" + orgMember.getOrg().getId() + "}";
        List<Object> postIds = redisTemplate.opsForList().range(key, 0, limit - 1);
        if (postIds != null && !postIds.isEmpty()) {
            List<PostDto> posts = new ArrayList<>();
            for (Object id : postIds) {
                postRepository.findById(Long.valueOf((String) id)).ifPresent(post -> posts.add(getPostDtoFromPostAndOrgMember(post, orgMember)));
            }
            return posts;
        }

        // If not found in redis, analyse and store in redis.
        TrendingPostsAnalyser trendingPostsAnalyser = trendingPostsAnalyserFactory.getTrendingPostsAnalyser(TRENDING_POSTS_ANALYSER_ALGORITHM);
        List<Post> newTrendingPosts = trendingPostsAnalyser.analyseTrendingPosts(orgMember.getOrg(), limit);

        if (newTrendingPosts.isEmpty()) {
            return List.of();
        }

        List<String> newTrendingPostIds = newTrendingPosts.stream().map(post -> post.getId().toString()).toList();
        try {
            redisTemplate.delete(key);  // Clear existing key if exists.
            redisTemplate.opsForList().rightPushAll(key, newTrendingPostIds.toArray(new String[0]));
            redisTemplate.expire(key, TRENDING_POSTS_CACHE_DURATION); // Set expiry same
        } catch (RedisSystemException ignored) {
        }
        return newTrendingPosts.stream().map(post -> getPostDtoFromPostAndOrgMember(post, orgMember)).toList();
    }

    /**
     * Searches posts based on a query string.
     *
     * @param query
     * @param page
     * @param size
     * @return
     */
    @Override
    public PostsListDto searchPosts(String query, int page, int size) {

        if (query.length() < 3) {
            throw new ActionProhibitedException("Search query must be at least 3 characters long.");
        }

        OrgMember orgMember = getCurrentOrgMember();

        String tsQuery = Arrays.stream(query.split("\\s+"))
                .map(word -> word.replaceAll("[^a-zA-Z0-9]", ""))
                .filter(word -> !word.isBlank())
                .collect(Collectors.joining(" | "));

        boolean hasPrivilegeToCreatePost = orgConfigurationService.hasPrivilegeTo(OrgAction.CREATE_POST, orgMember.getRole());

        Pageable pageable = PageRequest.of(page, size, Sort.by("created_at").descending());
        Slice<Post> posts = postRepository.searchPostsByOrg(orgMember.getOrg().getId(), tsQuery, pageable);
        List<PostDto> postDtos = new ArrayList<>(posts.getContent().stream().map(post -> getPostDtoFromPostAndOrgMember(post, orgMember)).toList());
        PostsListDto postsListDto = new PostsListDto();
        postsListDto.setInfo(Map.of("hasMoreRecords", posts.hasNext(), "hasPrivilegeToCreatePost", hasPrivilegeToCreatePost));
        postsListDto.setPosts(postDtos);
        return postsListDto;
    }

    /**
     * Archives a post by its ID.
     *
     * @param postId
     */
    @Override
    public void archivePost(String postId) {

        OrgMember orgMember = getCurrentOrgMember();
        Post post = getPostByPublicIdAndOrgId(postId, orgMember.getOrg().getId());
        if (!Objects.equals(post.getCreator().getId(), orgMember.getId())) {
            throw new PermissionDeniedException("You do not have permission to archive this post.");
        }
        if (post.getStatus().equals(PostStatus.ARCHIVED)) {
            throw new ActionProhibitedException("Post is already archived.");
        }
        post.setStatus(PostStatus.ARCHIVED);
        postRepository.save(post);
    }

    /**
     * Retrieves posts by a specific tag.
     *
     * @param tag
     * @param page
     * @param size
     * @param sortOrder
     * @return
     */
    @Override
    public PostsListDto getPostByTag(String tag, int page, int size, SortOrder sortOrder) {

        OrgMember orgMember = getCurrentOrgMember();
        Long orgMemberId = orgMember.getId();
        Long orgId = orgMember.getId();

        Pageable pageable = PageRequest.of(page, size);
        Page<PostTag> postPage = postTagService.findAllByTagAndOrgId(tag, orgId, pageable);
        List<PostDto> postDtos = new ArrayList<>(postPage.getContent().stream().map(postTag -> {
            Post post = postTag.getPost();
            OrgMember postOwnerOrgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(post.getCreator().getId(), orgId);
            PostDto postDto = getPostDtoFromPostAndOrgMember(post, orgMember);
            postDto.setCreator(post.getCreator().getId().equals(orgMemberId));
            postDto.setLiked(isLiked(post.getLikes(), orgMember.getUser()));
            return postDto;
        }).toList());
        PostsListDto postsListDto = new PostsListDto();
        postsListDto.setInfo(Map.of("hasMoreRecords", postPage.hasNext()));
        if (!postDtos.isEmpty() && postDtos.size() > size) {
            postDtos.remove(postDtos.size() - 1); // Remove the extra post if it exists
        }
        postsListDto.setPosts(postDtos);
        return postsListDto;
    }

    /**
     * Updates an existing post.
     *
     * @param postId
     * @param createPostDto
     * @return
     */
    @Override
    public PostDto updatePost(String postId, CreatePostDto createPostDto) {

        OrgMember orgMember = getCurrentOrgMember();

        validateCreatePostDto(createPostDto, orgMember);

        Post post = getPostByPublicIdAndOrgId(postId, orgMember.getOrg().getId());

        if (!post.getCreator().getPublicId().equals(orgMember.getPublicId())) {
            throw new PermissionDeniedException("You do not have permission to update this post.");
        }

        if (post.getStatus().equals(PostStatus.PUBLISHED)) {
            if (createPostDto.getScheduledAt() != null || PostStatus.SCHEDULED.equals(createPostDto.getStatus())) {
                throw new ActionProhibitedException("Cannot schedule a post that is already published.");
            }
        }

        createPostDto.applyToPost(post);

        postRepository.save(post);

        post.setTags(postTagService.updateTags(post, createPostDto.getTags()));
        post.setMentions(postMentionService.updateMentions(post, createPostDto.getMentions()));
        post.setPostMedia(postMediaService.updatePostMedia(post, createPostDto.getMediaIds()));

        if (createPostDto.getScheduledAt() != null) {
            redisTemplate.opsForZSet().remove(SCHEDULED_POST_KEY, post.getId());
            redisTemplate.opsForZSet().add(SCHEDULED_POST_KEY, post.getId(), DateTimeUtil.toEpochMilli(createPostDto.getScheduledAt()));
        }

        return getPostDtoFromPostAndOrgMember(post, orgMember);
    }

    /**
     * Deletes a post by its ID.
     *
     * @param postId
     */
    @Override
    public void deletePost(String postId) {

        OrgMember orgMember = getCurrentOrgMember();
        Post post = getPostByPublicIdAndOrgId(postId, orgMember.getOrg().getId());
        if (!post.getCreator().getId().equals(orgMember.getId())) {
            throw new PermissionDeniedException("You do not have permission to delete this post.");
        }
        if (post.isHasPoll()) {
            pollService.deleteByPost(post);
        }
        if (post.isMedia()) {
            postMediaService.unlinkMediaFromPost(post);
        }
        postRepository.delete(post);

        if (post.getStatus().equals(PostStatus.SCHEDULED)) {
            redisTemplate.opsForZSet().remove(SCHEDULED_POST_KEY, post.getId());
        }
    }

    /**
     * Get the post by its public id
     *
     * @param postId
     * @return
     */
    @Override
    public Post getPostByPublicId(String postId) {

        return postRepository.findByPublicId(postId).orElseThrow(() -> new DataNotFoundException("Post does not exist."));
    }

    /**
     * Get the post by its id
     *
     * @param postId
     * @return
     */
    public Post getPostById(Long postId) {

        return postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("Post does not exist."));
    }
}
