package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreatePostCommentDto;
import com.pesupal.server.dto.response.PostCommentDto;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostComment;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.PostCommentRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.PostCommentService;
import com.pesupal.server.service.interfaces.PostService;
import com.pesupal.server.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {

    private final PostService postService;
    private final UserService userService;
    private final OrgMemberService orgMemberService;
    private final PostCommentRepository postCommentRepository;

    /**
     * Creates a new comment on a post.
     *
     * @param createPostCommentDto
     * @param userId
     * @param orgId
     * @return PostCommentDto
     */
    @Override
    public PostCommentDto createPostComment(CreatePostCommentDto createPostCommentDto, Long userId, Long orgId) {

        Long postId = createPostCommentDto.getPostId();
        Post post = postService.getPostByIdAndOrgId(postId, orgId);

        if (!post.isCommentable()) {
            throw new ActionProhibitedException("Comments are not allowed on this post.");
        }

        if (!post.getStatus().equals(PostStatus.PUBLISHED)) {
            throw new ActionProhibitedException("Unable to comment on the post as it is not available.");
        }

        User user = userService.getUserById(userId);
        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);

        PostComment postComment = createPostCommentDto.toPostComment();
        postComment.setPost(post);
        postComment.setCommenter(user);
        return PostCommentDto.fromPostCommentAndOrgMember(postCommentRepository.save(postComment), orgMember);
    }

    /**
     * Deletes a comment on a post.
     *
     * @param commentId
     * @param userId
     * @param orgId
     */
    @Override
    public void deletePostComment(Long commentId, Long userId, Long orgId) {

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
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public List<PostCommentDto> getPostComments(Long postId, Long userId, Long orgId) {

        Post post = postService.getPostByIdAndOrgId(postId, orgId);
        if (!post.getStatus().equals(PostStatus.PUBLISHED)) {
            throw new ActionProhibitedException("Unable to retrieve comments as the post is not available.");
        }

        Map<Long, OrgMember> memo = new HashMap<>();

        return post.getComments().stream().map(postComment -> {

            Long commentedById = postComment.getCommenter().getId();
            if (!memo.containsKey(commentedById)) {
                memo.put(commentedById, orgMemberService.getOrgMemberByUserIdAndOrgId(commentedById, orgId));
            }
            return PostCommentDto.fromPostCommentAndOrgMember(postComment, memo.get(commentedById));
        }).toList();
    }

    /**
     * Retrieves a specific comment by its ID.
     *
     * @param commentId
     * @param userId
     * @param orgId
     * @return PostComment
     */
    @Override
    public PostComment getPostCommentById(Long commentId, Long userId, Long orgId) {

        return postCommentRepository.findById(commentId).orElseThrow(() -> new DataNotFoundException("Comment with ID " + commentId + " not found."));
    }
}
