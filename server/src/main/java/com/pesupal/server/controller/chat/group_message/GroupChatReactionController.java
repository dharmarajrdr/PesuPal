package com.pesupal.server.controller.chat.group_message;

import com.pesupal.server.dto.request.post.AddReactionDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.chat.ReactMessageResponseDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.chat.group_message.GroupChatReactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/group-chat-reactions")
public class GroupChatReactionController extends CurrentValueRetriever {

    private final GroupChatReactionService groupChatReactionService;

    @PostMapping("/{messageId}/react")
    public ResponseEntity<ApiResponseDto> reactToMessage(@PathVariable Long messageId, @RequestBody AddReactionDto addReactionDto) {

        ReactMessageResponseDto reactMessageResponseDto = groupChatReactionService.reactToMessage(messageId, addReactionDto.getReaction());
        return ResponseEntity.ok(new ApiResponseDto("Reaction added successfully", reactMessageResponseDto));
    }

    @DeleteMapping("/react/{reactionId}")
    public ResponseEntity<ApiResponseDto> unReactMessage(@PathVariable Long reactionId) {

        groupChatReactionService.unreactToMessage(reactionId);
        return ResponseEntity.ok(new ApiResponseDto("Reaction removed successfully"));
    }
}
