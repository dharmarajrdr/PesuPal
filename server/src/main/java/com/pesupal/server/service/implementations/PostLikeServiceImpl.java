package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.response.PostLikesDto;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostLike;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.PostLikeRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.PostLikeService;
import com.pesupal.server.service.interfaces.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class PostLikeServiceImpl extends CurrentValueRetriever implements PostLikeService {

    private final PostService postService;
    private final PostLikeRepository postLikeRepository;
    private final OrgMemberService orgMemberService;

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
            OrgMember likerOrgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(postLike.getLiker().getId(), orgMember.getOrg().getId());
            PostLikesDto postLikesDto = PostLikesDto.fromOrgMember(likerOrgMember);
            postLikesDto.setCreatedAt(postLike.getCreatedAt());
            return postLikesDto;
        }).toList();
    }
}
