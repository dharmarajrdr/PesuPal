package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateReplyCommentDto;
import com.pesupal.server.dto.response.ReplyCommentDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.post.PostComment;
import com.pesupal.server.model.post.PostReply;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.PostReplyRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.PostCommentService;
import com.pesupal.server.service.interfaces.PostReplyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PostReplyServiceImpl extends CurrentValueRetriever implements PostReplyService {

    private final OrgMemberService orgMemberService;
    private final PostCommentService postCommentService;
    private final PostReplyRepository postReplyRepository;

    /**
     * Creates a reply to a post comment.
     *
     * @param createReplyCommentDto
     * @return ReplyCommentDto
     */
    @Override
    public ReplyCommentDto createReplyComment(CreateReplyCommentDto createReplyCommentDto) {

        OrgMember orgMember = getCurrentOrgMember();
        PostComment postComment = postCommentService.getPostCommentById(createReplyCommentDto.getCommentId());

        PostReply postReply = createReplyCommentDto.toPostReply();
        postReply.setPostComment(postComment);
        postReply.setReplier(orgMember);
        return ReplyCommentDto.fromPostReplyAndOrgMember(postReplyRepository.save(postReply), orgMember);
    }

    /**
     * Retrieves replies for a specific comment.
     *
     * @param commentId
     * @return
     */
    @Override
    public List<ReplyCommentDto> getRepliesForComment(Long commentId) {

        OrgMember orgMember = getCurrentOrgMember();
        Long orgId = orgMember.getOrg().getId();
        PostComment postComment = postCommentService.getPostCommentById(commentId);

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
     */
    @Override
    public void deleteReply(Long replyId) {

        OrgMember orgMember = getCurrentOrgMember();

        PostReply postReply = postReplyRepository.findById(replyId).orElseThrow(() -> new DataNotFoundException("Reply with ID " + replyId + " not found."));

        if (!Objects.equals(postReply.getReplier().getId(), orgMember.getId())) {
            throw new PermissionDeniedException("You do not have permission to delete this reply.");
        }

        postReplyRepository.delete(postReply);
    }
}
