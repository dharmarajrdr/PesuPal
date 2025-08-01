package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreateReplyCommentDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.ReplyCommentDto;
import com.pesupal.server.service.interfaces.PostReplyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/post")
public class PostReplyController {

    private final PostReplyService postReplyService;

    @GetMapping("/comment/{commentId}/reply")
    public ResponseEntity<ApiResponseDto> getRepliesForComment(@PathVariable Long commentId) {

        List<ReplyCommentDto> replyCommentDtos = postReplyService.getRepliesForComment(commentId);
        return ResponseEntity.ok().body(new ApiResponseDto("Replies fetched successfully", replyCommentDtos));
    }

    @PostMapping("/reply")
    public ResponseEntity<ApiResponseDto> replyToComment(@RequestBody CreateReplyCommentDto createReplyCommentDto) {

        ReplyCommentDto replyCommentDto = postReplyService.createReplyComment(createReplyCommentDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Reply created successfully", replyCommentDto));
    }

    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<ApiResponseDto> deleteReply(@PathVariable Long replyId) {

        postReplyService.deleteReply(replyId);
        return ResponseEntity.ok().body(new ApiResponseDto("Reply deleted successfully"));
    }

}
