package com.pesupal.server.controller;

import com.pesupal.server.dto.request.AddReactionDto;
import com.pesupal.server.dto.request.GetConversationBetweenUsers;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.DirectMessageResponseDto;
import com.pesupal.server.dto.response.ReactMessageResponseDto;
import com.pesupal.server.service.interfaces.DirectMessageReactionService;
import com.pesupal.server.service.interfaces.DirectMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/direct-messages")
public class DirectMessageController {

    private final DirectMessageService directMessageService;
    private final DirectMessageReactionService directMessageReactionService;

    @GetMapping("/{chatId}")
    public ResponseEntity<ApiResponseDto> getDirectMessagesByUserId(@PathVariable String chatId, @RequestParam Integer page, @RequestParam Integer size) {

        GetConversationBetweenUsers getConversationBetweenUsers = new GetConversationBetweenUsers(chatId, page, size);
        List<DirectMessageResponseDto> directMessageResponseDtos = directMessageService.getDirectMessagesBetweenUsers(getConversationBetweenUsers);
        return ResponseEntity.ok(new ApiResponseDto("Direct messages retrieved successfully", directMessageResponseDtos));
    }

    @PutMapping("/{chatId}/read_all")
    public ResponseEntity<ApiResponseDto> markAllMessagesAsRead(@PathVariable String chatId, @RequestParam Long userId) {

        directMessageService.markAllMessagesAsRead(chatId, userId);
        return ResponseEntity.ok(new ApiResponseDto("All messages marked as read successfully"));
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponseDto> deleteMessage(@PathVariable Long messageId, @RequestParam Long userId) {

        directMessageService.deleteMessage(userId, messageId);
        return ResponseEntity.ok(new ApiResponseDto("Message deleted successfully"));
    }

    @PostMapping("/{messageId}/react")
    public ResponseEntity<ApiResponseDto> reactToMessage(@PathVariable Long messageId, @RequestBody AddReactionDto addReactionDto) {

        ReactMessageResponseDto reactMessageResponseDto = directMessageReactionService.reactToMessage(messageId, addReactionDto);
        return ResponseEntity.ok(new ApiResponseDto("Reaction added successfully", reactMessageResponseDto));
    }

    @DeleteMapping("/react/{reactionId}")
    public ResponseEntity<ApiResponseDto> unReactMessage(@RequestParam Long userId, @PathVariable Long reactionId) {

        directMessageReactionService.unreactToMessage(reactionId, userId);
        return ResponseEntity.ok(new ApiResponseDto("Reaction removed successfully"));
    }
}
