package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreatePostCommentDto;
import com.pesupal.server.dto.response.PostCommentDto;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostComment;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.PostCommentRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.PostCommentService;
import com.pesupal.server.service.interfaces.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class PostCommentServiceImpl extends CurrentValueRetriever implements PostCommentService {

    private final PostService postService;
    private final OrgMemberService orgMemberService;
    private final PostCommentRepository postCommentRepository;

    /**
     * Creates a new comment on a post.
     *
     * @param createPostCommentDto
     * @return PostCommentDto
     */
    @Override
    public PostCommentDto createPostComment(CreatePostCommentDto createPostCommentDto) {

        OrgMember commenter = getCurrentOrgMember();
        Long orgId = commenter.getOrg().getId();

        Long postId = createPostCommentDto.getPostId();
        Post post = postService.getPostByIdAndOrgId(postId, orgId);

        if (!post.isCommentable()) {
            throw new ActionProhibitedException("Comments are not allowed on this post.");
        }

        if (!post.getStatus().equals(PostStatus.PUBLISHED)) {
            throw new ActionProhibitedException("Unable to comment on the post as it is not available.");
        }

        PostComment postComment = createPostCommentDto.toPostComment();
        postComment.setPost(post);
        postComment.setCommenter(commenter);
        PostCommentDto postCommentDto = PostCommentDto.fromPostCommentAndOrgMember(postCommentRepository.save(postComment), commenter);
        postCommentDto.setDeletable(true);
        return postCommentDto;
    }

    /**
     * Deletes a comment on a post.
     *
     * @param commentId
     */
    @Override
    public void deletePostComment(Long commentId) {

        OrgMember orgMember = getCurrentOrgMember();
        Long userId = orgMember.getUser().getId();

        PostComment postComment = postCommentRepository.findById(commentId).orElseThrow(() -> new DataNotFoundException("Comment with ID " + commentId + " not found."));

        if (!Objects.equals(postComment.getCommenter().getId(), userId)) {
            throw new ActionProhibitedException("You are not allowed to delete this comment.");
        }

        if (!postComment.getPost().getStatus().equals(PostStatus.PUBLISHED)) {
            throw new ActionProhibitedException("Unable to delete the comment as the post is not available.");
        }

        postCommentRepository.delete(postComment);
    }

    /**
     * Retrieves comments for a specific post.
     *
     * @param postId
     * @return
     */
    @Override
    public List<PostCommentDto> getPostComments(String postId) {

        OrgMember orgMember = getCurrentOrgMember();
        Long orgId = orgMember.getOrg().getId();
        Long userId = orgMember.getUser().getId();

        Post post = postService.getPostByPublicId(postId);
        if (!post.getStatus().equals(PostStatus.PUBLISHED)) {
            throw new ActionProhibitedException("Unable to retrieve comments as the post is not available.");
        }

        Map<Long, OrgMember> memo = new HashMap<>();

        List<PostCommentDto> postCommentDtos = new ArrayList<>(post.getComments().stream().map(postComment -> {

            Long commentedById = postComment.getCommenter().getId();
            if (!memo.containsKey(commentedById)) {
                memo.put(commentedById, orgMemberService.getOrgMemberByUserIdAndOrgId(commentedById, orgId));
            }
            PostCommentDto postCommentDto = PostCommentDto.fromPostCommentAndOrgMember(postComment, memo.get(commentedById));
            postCommentDto.setDeletable(commentedById.equals(userId));
            return postCommentDto;
        }).toList());
        postCommentDtos.sort(Comparator.comparing(PostCommentDto::getCreatedAt).reversed());
        return postCommentDtos;
    }

    /**
     * Retrieves a specific comment by its ID.
     *
     * @param commentId
     * @return PostComment
     */
    @Override
    public PostComment getPostCommentById(Long commentId) {

        return postCommentRepository.findById(commentId).orElseThrow(() -> new DataNotFoundException("Comment with ID " + commentId + " not found."));
    }
}
