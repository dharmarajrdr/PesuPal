package com.pesupal.server.service.implementations.post;

import com.pesupal.server.dto.response.post.PostDto;
import com.pesupal.server.dto.response.post.PostLikesDto;
import com.pesupal.server.dto.response.post.PostsListDto;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostLike;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.post.PostLikeRepository;
import com.pesupal.server.service.interfaces.MediaService;
import com.pesupal.server.service.interfaces.post.PostLikeService;
import com.pesupal.server.service.interfaces.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class PostLikeServiceImpl extends CurrentValueRetriever implements PostLikeService {

    private final PostService postService;
    private final MediaService mediaService;
    private final PostLikeRepository postLikeRepository;

    /**
     * Likes a post by the current user in the current organization.
     *
     * @param postId
     */
    @Override
    public void likePost(String postId) {

        OrgMember orgMember = getCurrentOrgMember();
        Post post = postService.getPostByPublicIdAndOrgId(postId, orgMember.getOrg().getId());
        if (!post.getStatus().equals(PostStatus.PUBLISHED)) {
            throw new ActionProhibitedException("The post that you are trying to like may be deleted or unpublished by the author.");
        }
        if (postLikeRepository.existsByPostAndLiker(post, orgMember)) {
            throw new ActionProhibitedException("You have already liked this post.");
        }
        PostLike postLike = new PostLike();
        postLike.setPost(post);
        postLike.setLiker(orgMember);
        postLikeRepository.save(postLike);
    }

    /**
     * Unlikes a post by the current user in the current organization.
     *
     * @param postId
     */
    @Override
    public void unlikePost(String postId) {

        OrgMember orgMember = getCurrentOrgMember();
        Post post = postService.getPostByPublicIdAndOrgId(postId, orgMember.getOrg().getId());
        if (!post.getStatus().equals(PostStatus.PUBLISHED)) {
            throw new ActionProhibitedException("The post that you are trying to unlike may be deleted or unpublished by the author.");
        }
        PostLike postLike = postLikeRepository.findByPostAndLiker(post, orgMember).orElseThrow(() -> new ActionProhibitedException("You have not liked this post."));
        postLikeRepository.delete(postLike);
    }

    /**
     * Retrieves a list of likes for a post by the current user in the current organization.
     *
     * @param postId
     * @return List<PostLikesDto>
     */
    @Override
    public List<PostLikesDto> getPostLikes(String postId) {

        OrgMember orgMember = getCurrentOrgMember();
        return postLikeRepository.findByPostPublicIdAndPost_OrgId(postId, orgMember.getOrg().getId()).stream().map(postLike -> {
            OrgMember likerOrgMember = postLike.getLiker();
            PostLikesDto postLikesDto = PostLikesDto.fromOrgMember(likerOrgMember);
            postLikesDto.setDisplayPicture(mediaService.generatePresignedUrl(likerOrgMember.getDisplayPicture()));
            postLikesDto.setCreatedAt(postLike.getCreatedAt());
            return postLikesDto;
        }).toList();
    }

    /**
     * Retrieves all posts liked by the current user in the current organization.
     *
     * @return
     */
    @Override
    public PostsListDto getAllLikedPosts(int page, int size, Sort sort) {

        PostsListDto postsListDto = new PostsListDto();
        OrgMember orgMember = getCurrentOrgMember();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PostLike> likes = postLikeRepository.findAllByLiker(orgMember, pageable);
        postsListDto.setPosts(likes.getContent().stream().map(postLike -> {
            PostDto postDto = postService.getPostDtoFromPostAndOrgMember(postLike.getPost(), orgMember);
            postDto.setLiked(true);
            return postDto;
        }).toList());
        postsListDto.setInfo(Map.of("hasMoreRecords", likes.hasNext()));
        return postsListDto;
    }
}
