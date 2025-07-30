package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreatePostDto;
import com.pesupal.server.dto.response.*;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostLike;
import com.pesupal.server.model.post.PostMedia;
import com.pesupal.server.model.post.PostTag;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.PostRepository;
import com.pesupal.server.service.interfaces.*;
import com.pesupal.server.strategies.media_storage.S3Service;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl extends CurrentValueRetriever implements PostService {

    private final S3Service s3Service;
    private final TagService tagService;
    private final PollService pollService;
    private final PostRepository postRepository;
    private final PostTagService postTagService;
    private final OrgMemberService orgMemberService;

    /**
     * Creates a new post.
     *
     * @param createPostDto
     */
    @Override
    @Transactional
    public Post createPost(CreatePostDto createPostDto) {

        OrgMember creator = getCurrentOrgMember();

        boolean hasPoll = createPostDto.getPoll() != null;

        Post post = createPostDto.toPost();
        post.setOrg(creator.getOrg());
        post.setCreator(creator);
        post.setStatus(PostStatus.PUBLISHED);
        List<PostMedia> postMedia = createPostDto.getMediaIds().stream().map(mediaId -> PostMedia.builder().post(post).mediaId(mediaId.getId()).extension(mediaId.getExtension()).build()).collect(Collectors.toList());
        post.setMedia(!postMedia.isEmpty());
        post.setPostMedia(postMedia);
        List<PostTag> postTags = createPostDto.getTags().stream().map(tagName -> PostTag.builder().post(post).tag(tagService.createOrGet(tagName)).build()).collect(Collectors.toList());
        post.setTags(postTags);
        post.setHasPoll(hasPoll);
        postRepository.save(post);
        if (hasPoll) {
            pollService.createPoll(createPostDto.getPoll(), post);
        }
        return post;
    }

    private boolean isLiked(List<PostLike> likes, User user) {
        return likes.stream().anyMatch(like -> Objects.equals(like.getLiker().getId(), user.getId()));
    }

    /**
     * Converts a Post entity and OrgMember to a PostDto.
     *
     * @param post
     * @param orgMember
     * @return PostDto
     */
    private PostDto getPostDtoFromPostAndOrgMember(Post post, OrgMember orgMember) {

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
     * @param org
     * @return Post
     */
    @Override
    public Post getPostByIdAndOrg(Long postId, Org org) {

        return postRepository.findByIdAndOrg(postId, org).orElseThrow(() -> new DataNotFoundException("Post with ID " + postId + " does not exist."));
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
        Pageable pageable = PageRequest.of(page, size + 1, sort);
        Page<Post> postPage = postRepository.findAllByOrgIdAndCreator_PublicIdAndStatus(orgId, creatorId, pageable, PostStatus.PUBLISHED);

        List<PostDto> postDtos = new ArrayList<>(postPage.getContent().stream().map(post -> {
            PostDto postDto = getPostDtoFromPostAndOrgMember(post, creator);
            postDto.setCreator(post.getCreator().getId().equals(orgMember.getId()));
            postDto.setLiked(isLiked(post.getLikes(), orgMember.getUser()));
            return postDto;
        }).toList());
        PostsListDto postsListDto = new PostsListDto();
        postsListDto.setInfo(Map.of(
                "hasMoreRecords", postDtos.size() == size + 1
        ));
        if (!postDtos.isEmpty() && postDtos.size() > size) {
            postDtos.remove(postDtos.size() - 1); // Remove the extra post if it exists
        }
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

        Pageable pageable = PageRequest.of(page, size + 1);
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
        postsListDto.setInfo(Map.of(
                "hasMoreRecords", postDtos.size() == size + 1
        ));
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
        createPostDto.applyToPost(post);
        return postRepository.save(post);
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
        postRepository.delete(post);
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
}
