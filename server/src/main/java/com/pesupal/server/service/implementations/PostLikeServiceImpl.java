package com.pesupal.server.service.implementations;

import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostLike;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.PostLikeRepository;
import com.pesupal.server.service.interfaces.PostLikeService;
import com.pesupal.server.service.interfaces.PostService;
import com.pesupal.server.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostService postService;
    private final UserService userService;

    /**
     * Likes a post by the current user in the current organization.
     *
     * @param postId
     * @param userId
     * @param orgId
     */
    @Override
    public void likePost(Long postId, Long userId, Long orgId) {

        Post post = postService.getPostByIdAndOrgId(postId, orgId);
        User user = userService.getUserById(userId);
        if (!post.getStatus().equals(PostStatus.PUBLISHED)) {
            throw new ActionProhibitedException("The post that you are trying to like may be deleted or unpublished by the author.");
        }
        if (postLikeRepository.existsByPostAndLiker(post, user)) {
            throw new ActionProhibitedException("You have already liked this post.");
        }
        PostLike postLike = new PostLike();
        postLike.setPost(post);
        postLike.setLiker(user);
        postLikeRepository.save(postLike);
    }

    /**
     * Unlikes a post by the current user in the current organization.
     *
     * @param postId
     * @param userId
     * @param orgId
     */
    @Override
    public void unlikePost(Long postId, Long userId, Long orgId) {

        Post post = postService.getPostByIdAndOrgId(postId, orgId);
        User user = userService.getUserById(userId);
        if (!post.getStatus().equals(PostStatus.PUBLISHED)) {
            throw new ActionProhibitedException("The post that you are trying to unlike may be deleted or unpublished by the author.");
        }
        PostLike postLike = postLikeRepository.findByPostAndLiker(post, user).orElseThrow(() -> new ActionProhibitedException("You have not liked this post."));
        postLikeRepository.delete(postLike);
    }
}
