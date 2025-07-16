package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateReplyCommentDto;
import com.pesupal.server.dto.response.ReplyCommentDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.post.PostComment;
import com.pesupal.server.model.post.PostReply;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.PostReplyRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.PostCommentService;
import com.pesupal.server.service.interfaces.PostReplyService;
import com.pesupal.server.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PostReplyServiceImpl implements PostReplyService {

    private final UserService userService;
    private final OrgMemberService orgMemberService;
    private final PostCommentService postCommentService;
    private final PostReplyRepository postReplyRepository;

    /**
     * Creates a reply to a post comment.
     *
     * @param createReplyCommentDto
     * @param userId
     * @param orgId
     * @return ReplyCommentDto
     */
    @Override
    public ReplyCommentDto createReplyComment(CreateReplyCommentDto createReplyCommentDto, Long userId, Long orgId) {

        User replier = userService.getUserById(userId);
        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        PostComment postComment = postCommentService.getPostCommentById(createReplyCommentDto.getCommentId(), userId, orgId);

        PostReply postReply = createReplyCommentDto.toPostReply();
        postReply.setPostComment(postComment);
        postReply.setReplier(replier);
        return ReplyCommentDto.fromPostReplyAndOrgMember(postReplyRepository.save(postReply), orgMember);
    }

    /**
     * Retrieves replies for a specific comment.
     *
     * @param commentId
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public List<ReplyCommentDto> getRepliesForComment(Long commentId, Long userId, Long orgId) {

        PostComment postComment = postCommentService.getPostCommentById(commentId, userId, orgId);

        Map<Long, OrgMember> memo = new HashMap<>();

        return postComment.getReplies().stream().map(postReply -> {

            Long replierId = postReply.getReplier().getId();
            if (!memo.containsKey(replierId)) {
                memo.put(replierId, orgMemberService.getOrgMemberByUserIdAndOrgId(replierId, orgId));
            }
            return ReplyCommentDto.fromPostReplyAndOrgMember(postReply, memo.get(replierId));
        }).toList();

    }

    /**
     * Deletes a reply to a post comment.
     *
     * @param replyId
     * @param userId
     * @param orgId
     */
    @Override
    public void deleteReply(Long replyId, Long userId, Long orgId) {

        PostReply postReply = postReplyRepository.findById(replyId).orElseThrow(() -> new DataNotFoundException("Reply with ID " + replyId + " not found."));

        if (!Objects.equals(postReply.getReplier().getId(), userId)) {
            throw new PermissionDeniedException("You do not have permission to delete this reply.");
        }

        postReplyRepository.delete(postReply);
    }
}
