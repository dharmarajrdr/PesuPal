package com.pesupal.server.service.implementations.post;

import com.pesupal.server.dto.request.post.CreatePostDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.post.*;
import com.pesupal.server.enums.FeedRetriever;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.factory.FeedRetrieverServiceFactory;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.DateTimeUtil;
import com.pesupal.server.model.post.*;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.post.PostRepository;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import com.pesupal.server.service.interfaces.post.*;
import com.pesupal.server.strategies.media_storage.S3Service;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class PostServiceImpl extends CurrentValueRetriever implements PostService {

    private final S3Service s3Service;
    private final TagService tagService;
    private final PollService pollService;
    private final PostRepository postRepository;
    private final PostTagService postTagService;
    private final OrgMemberService orgMemberService;
    private final PostMediaService postMediaService;
    private final PostMentionService postMentionService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final FeedRetrieverServiceFactory feedRetrieverServiceFactory;

    private final static String SCHEDULED_POST_KEY = "scheduled_posts";
    private final static FeedRetriever feedRetrieverAlgorithm = FeedRetriever.SIMPLE_FEED_RETRIEVER_ALGORITHM;

    /**
     * Creates a new post - Internal use only.
     *
     * @param createPostDto
     */
    @Transactional
    public Post createPostInternal(CreatePostDto createPostDto) {

        OrgMember creator = getCurrentOrgMember();

        boolean hasPoll = createPostDto.getPoll() != null;

        Post post = createPostDto.toPost();
        post.setOrg(creator.getOrg());
        post.setCreator(creator);
        post.setMedia(!createPostDto.getMediaIds().isEmpty());
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
        PostDto postDto = getPostDtoFromPostAndOrgMember(post, orgMember);
        postDto.setCreator(true);
        postDto.setLiked(false);
        return postDto;
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
        PostDto postDto = getPostDtoFromPostAndOrgMember(post, orgMember);
        postDto.setCreator(true);
        postDto.setLiked(false);
        return postDto;
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
            userPreviews.add(UserPreviewDto.fromOrgMember(member));
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
        postDto.setTags(post.getTags().stream().map(postTag -> postTag.getTag().getName()).toList());
        postDto.setMedia(post.getPostMedia().stream().map(postMedia -> {
            String key = postMedia.getMediaId() + "." + postMedia.getExtension();
            return s3Service.generatePresignedUrl(key);
        }).toList());
        postDto.setOwner(UserBasicInfoDto.fromOrgMember(orgMember));
        postDto.setImpression(PostImpressionDto.builder().likes(post.getLikes().size()).comments(post.getComments().size()).build());
        postDto.setBookmarked(false);   // Feature not implemented yet
        if (post.isHasPoll()) {
            postDto.setPoll(PollDto.fromPoll(pollService.getPollByPost(post), orgMember.getId()));
        }
        if (post.getPostMentionLabel() != null) {
            postDto.setMentions(new PostMentionsDto(post.getPostMentionLabel(), getUniqueMentions(post.getMentions())));
        }
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

        return postRepository.findByIdAndOrgId(postId, orgId).orElseThrow(() -> new DataNotFoundException("Post with ID " + postId + " does not exist."));
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
        OrgMember postOwner = orgMemberService.getOrgMemberByUserIdAndOrgId(post.getCreator().getId(), orgId);
        PostDto postDto = getPostDtoFromPostAndOrgMember(post, postOwner);
        postDto.setLiked(isLiked(post.getLikes(), orgMember.getUser()));
        return postDto;
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

        return postRepository.findByPublicIdAndOrgId(postId, orgId).orElseThrow(() -> new DataNotFoundException("Post with ID " + postId + " does not exist."));
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

        OrgMember creator = orgMemberService.getOrgMemberByPublicId(creatorId);

        Sort sort = Sort.by(sortOrder == SortOrder.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage = postRepository.findAllByOrgIdAndCreator_PublicIdAndStatus(orgId, creatorId, pageable, PostStatus.PUBLISHED);

        List<PostDto> postDtos = new ArrayList<>(postPage.getContent().stream().map(post -> {
            PostDto postDto = getPostDtoFromPostAndOrgMember(post, creator);
            postDto.setCreator(post.getCreator().getId().equals(orgMember.getId()));
            postDto.setLiked(isLiked(post.getLikes(), orgMember.getUser()));
            return postDto;
        }).toList());
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
        List<PostDto> postDtos = new ArrayList<>(posts.getContent().stream().map(post -> {
            PostDto postDto = getPostDtoFromPostAndOrgMember(post, orgMember);
            postDto.setCreator(true);
            postDto.setLiked(false);
            return postDto;
        }).toList());

        PostsListDto postsListDto = new PostsListDto();
        postsListDto.setInfo(Map.of("hasMoreRecords", posts.hasNext()));
        postsListDto.setPosts(postDtos);
        return postsListDto;
    }

    /**
     * Retrieves the feed for the current user.
     *
     * @param page
     * @param size
     * @param sortOrder
     * @return
     */
    @Override
    public PostsListDto getFeeds(int page, int size, SortOrder sortOrder) {

        FeedRetrieverService feedRetrieverService = feedRetrieverServiceFactory.getFeedRetrieverService(feedRetrieverAlgorithm);
        return feedRetrieverService.getFeeds(page, size, sortOrder, getCurrentOrgMember());
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
            PostDto postDto = getPostDtoFromPostAndOrgMember(post, postOwnerOrgMember);
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
    public Post updatePost(String postId, CreatePostDto createPostDto) {

        OrgMember orgMember = getCurrentOrgMember();

        Post post = getPostByPublicIdAndOrgId(postId, orgMember.getOrg().getId());

        if (post.getStatus().equals(PostStatus.PUBLISHED)) {
            if (createPostDto.getScheduledAt() != null || PostStatus.SCHEDULED.equals(createPostDto.getStatus())) {
                throw new ActionProhibitedException("Cannot schedule a post that is already published.");
            }
        }

        createPostDto.applyToPost(post);
        postRepository.save(post);

        if (createPostDto.getScheduledAt() != null) {
            redisTemplate.opsForZSet().remove(SCHEDULED_POST_KEY, post.getId());
            redisTemplate.opsForZSet().add(SCHEDULED_POST_KEY, post.getId(), DateTimeUtil.toEpochMilli(createPostDto.getScheduledAt()));
        }
        return post;
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

        return postRepository.findByPublicId(postId).orElseThrow(() -> new DataNotFoundException("Post with id " + postId + " does not exist."));
    }

    /**
     * Get the post by its id
     *
     * @param postId
     * @return
     */
    public Post getPostById(Long postId) {

        return postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("Post with id " + postId + " does not exist."));
    }
}
