package com.pesupal.server.controller;

import com.pesupal.server.dto.request.AddReactionDto;
import com.pesupal.server.dto.request.ChatMessageDto;
import com.pesupal.server.dto.request.GetConversationBetweenUsers;
import com.pesupal.server.dto.response.*;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.DirectMessageReactionService;
import com.pesupal.server.service.interfaces.DirectMessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/direct-messages")
public class DirectMessageController extends CurrentValueRetriever {

    private final DirectMessageService directMessageService;
    private final DirectMessageReactionService directMessageReactionService;

    @GetMapping("/{chatId}")
    public ResponseEntity<ApiResponseDto> getDirectMessagesByUserId(@PathVariable String chatId, @RequestParam Integer page, @RequestParam Integer size, @RequestParam(name = "pivot_message_id", required = false) Long pivotMessageId) {

        GetConversationBetweenUsers getConversationBetweenUsers = new GetConversationBetweenUsers(chatId, pivotMessageId, page, size);
        List<MessageDto> directMessageResponseDtos = directMessageService.getDirectMessagesBetweenUsers(getConversationBetweenUsers, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok(new ApiResponseDto("Direct messages retrieved successfully", directMessageResponseDtos));
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponseDto> getRecentChats(@RequestParam Integer page, @RequestParam Integer size) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        RecentChatPagedDto recentChats = directMessageService.getRecentChatsPaged(getCurrentOrgMember(), pageable);
        return ResponseEntity.ok(new ApiResponseDto("Recent chats retrieved successfully", recentChats.getChats(), recentChats.getPageable()));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponseDto> sendDirectMessage(@RequestBody ChatMessageDto chatMessageDto) {

        directMessageService.save(chatMessageDto);
        return ResponseEntity.ok(new ApiResponseDto("Direct message sent successfully"));
    }

    @PutMapping("/{chatId}/read-all")
    public ResponseEntity<ApiResponseDto> markAllMessagesAsRead(@PathVariable String chatId) {

        directMessageService.markAllMessagesAsRead(chatId);
        return ResponseEntity.ok(new ApiResponseDto("All messages marked as read successfully"));
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponseDto> deleteMessage(@PathVariable Long messageId) {

        directMessageService.deleteMessage(messageId);
        return ResponseEntity.ok(new ApiResponseDto("Message deleted successfully"));
    }

    @PostMapping("/{messageId}/react")
    public ResponseEntity<ApiResponseDto> reactToMessage(@PathVariable Long messageId, @RequestBody AddReactionDto addReactionDto) {

        ReactMessageResponseDto reactMessageResponseDto = directMessageReactionService.reactToMessage(messageId, addReactionDto.getReaction());
        return ResponseEntity.ok(new ApiResponseDto("Reaction added successfully", reactMessageResponseDto));
    }

    @DeleteMapping("/react/{reactionId}")
    public ResponseEntity<ApiResponseDto> unReactMessage(@PathVariable Long reactionId) {

        directMessageReactionService.unreactToMessage(reactionId);
        return ResponseEntity.ok(new ApiResponseDto("Reaction removed successfully"));
    }

    @GetMapping("/preview/{chatId}")
    public ResponseEntity<ApiResponseDto> getDirectMessagePreview(@PathVariable String chatId) {

        ChatPreviewDto directMessagePreviewDto = directMessageService.getDirectMessagePreviewByChatId(chatId);
        return ResponseEntity.ok(new ApiResponseDto("Direct message preview retrieved successfully", directMessagePreviewDto));
    }
}
