package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreatePostDto;
import com.pesupal.server.dto.response.PostDto;
import com.pesupal.server.dto.response.PostImpressionDto;
import com.pesupal.server.dto.response.PostsListDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostLike;
import com.pesupal.server.model.post.PostMedia;
import com.pesupal.server.model.post.PostTag;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.PostRepository;
import com.pesupal.server.service.interfaces.*;
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
public class PostServiceImpl implements PostService {

    private final OrgService orgService;
    private final TagService tagService;
    private final UserService userService;
    private final PostRepository postRepository;
    private final OrgMemberService orgMemberService;

    /**
     * Creates a new post.
     *
     * @param createPostDto
     */
    @Override
    public Post createPost(CreatePostDto createPostDto, Long userId, Long orgId) {

        Org org = orgService.getOrgById(orgId);
        User user = userService.getUserById(userId);

        orgMemberService.validateUserIsOrgMember(user, org);

        Post post = createPostDto.toPost();
        post.setOrg(org);
        post.setUser(user);
        post.setStatus(PostStatus.PUBLISHED);
        List<PostMedia> postMedia = createPostDto.getMediaIds().stream().map(mediaId -> PostMedia.builder().post(post).mediaId(mediaId).build()).collect(Collectors.toList());
        post.setMedia(!postMedia.isEmpty());
        post.setPostMedia(postMedia);
        List<PostTag> postTags = createPostDto.getTags().stream().map(tagName -> PostTag.builder().post(post).tag(tagService.createOrGet(tagName)).build()).collect(Collectors.toList());
        post.setTags(postTags);
        return postRepository.save(post);
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
        postDto.setMedia(post.getPostMedia().stream().map(PostMedia::getMediaId).toList());
        postDto.setOwner(UserBasicInfoDto.fromOrgMember(orgMember));
        postDto.setImpression(PostImpressionDto.builder().likes(post.getLikes().size()).comments(post.getComments().size()).build());
        postDto.setLiked(isLiked(post.getLikes(), orgMember.getUser()));
        postDto.setBookmarked(false);   // Feature not implemented yet
        return postDto;
    }

    /**
     * Checks if a post exists in the organization.
     *
     * @param postId
     * @param orgId
     * @return
     */
    @Override
    public boolean isPostExistInOrg(Long postId, Long orgId) {

        return postRepository.existsByIdAndOrgId(postId, orgId);
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
     * @param userId
     * @param orgId
     * @return PostDto
     */
    @Override
    public PostDto getPostByIdAndOrgId(Long postId, Long userId, Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        Post post = getPostByIdAndOrgId(postId, orgId);
        return getPostDtoFromPostAndOrgMember(post, orgMember);
    }

    /**
     * Retrieves a list of posts by user ID and organization ID.
     *
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public PostsListDto getPostByUserId(Long userId, Long orgId, Long postOwnerId, int page, int size, SortOrder sortOrder) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);

        orgMemberService.validateUserIsOrgMember(postOwnerId, orgId);

        Sort sort = Sort.by(sortOrder == SortOrder.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size + 1, sort);
        Page<Post> postPage = postRepository.findAllByOrgIdAndUserIdAndStatus(orgId, postOwnerId, pageable, PostStatus.PUBLISHED);
        OrgMember postOwnerOrgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(postOwnerId, orgId);
        List<PostDto> postDtos = new ArrayList<>(postPage.getContent().stream().map(post -> getPostDtoFromPostAndOrgMember(post, postOwnerOrgMember)).toList());
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
     * @param userId
     * @param orgId
     */
    @Override
    public void archivePost(Long postId, Long userId, Long orgId) {

        Post post = getPostByIdAndOrgId(postId, orgId);
        if (!Objects.equals(post.getUser().getId(), userId)) {
            throw new PermissionDeniedException("You do not have permission to archive this post.");
        }
        if (post.getStatus().equals(PostStatus.ARCHIVED)) {
            throw new ActionProhibitedException("Post is already archived.");
        }
        post.setStatus(PostStatus.ARCHIVED);
        postRepository.save(post);
    }

    /**
     * Retrieves the count of posts made by a user in a specific organization.
     *
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public int getUserPostCount(Long userId, Long orgId) {

        return postRepository.countAllByUserIdAndOrgId(userId, orgId);
    }
}
