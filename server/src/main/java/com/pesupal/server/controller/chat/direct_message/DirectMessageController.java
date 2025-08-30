package com.pesupal.server.controller.chat.direct_message;

import com.pesupal.server.dto.request.chat.RescheduleMessageDto;
import com.pesupal.server.dto.request.chat.direct_message.ChatMessageDto;
import com.pesupal.server.dto.request.chat.direct_message.GetConversationBetweenUsers;
import com.pesupal.server.dto.request.post.AddReactionDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.chat.ChatPreviewDto;
import com.pesupal.server.dto.response.chat.MessageDto;
import com.pesupal.server.dto.response.chat.ReactMessageResponseDto;
import com.pesupal.server.dto.response.chat.RecentChatPagedDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.chat.direct_message.DirectMessage;
import com.pesupal.server.service.interfaces.chat.direct_message.DirectMessageReactionService;
import com.pesupal.server.service.interfaces.chat.direct_message.DirectMessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        List<MessageDto> directMessageResponseDtos = directMessageService.getDirectMessagesBetweenUsers(getConversationBetweenUsers);
        return ResponseEntity.ok(new ApiResponseDto("Direct messages retrieved successfully", directMessageResponseDtos));
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponseDto> getRecentChats(@RequestParam(required = false, defaultValue = "") String search, @RequestParam Integer page, @RequestParam Integer size) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        RecentChatPagedDto recentChats = directMessageService.getRecentChatsPaged(search, pageable);
        return ResponseEntity.ok(new ApiResponseDto("Recent chats retrieved successfully", recentChats.getChats(), recentChats.getPageable()));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponseDto> sendDirectMessage(@RequestBody ChatMessageDto<DirectMessage> chatMessageDto) {

        directMessageService.save(chatMessageDto);
        return ResponseEntity.ok(new ApiResponseDto("Direct message sent successfully"));
    }

    @PostMapping("/schedule")
    public ResponseEntity<ApiResponseDto> scheduleDirectMessage(@RequestBody ChatMessageDto<DirectMessage> chatMessageDto) {

        directMessageService.schedule(chatMessageDto);
        return ResponseEntity.ok(new ApiResponseDto("Direct message scheduled successfully"));
    }

    @PatchMapping("/reschedule/{messageId}")
    public ResponseEntity<ApiResponseDto> rescheduleDirectMessage(@PathVariable Long messageId, @RequestBody RescheduleMessageDto rescheduleMessageDto) {

        directMessageService.reschedule(messageId, rescheduleMessageDto);
        return ResponseEntity.ok(new ApiResponseDto("Direct message rescheduled successfully"));
    }

    @PatchMapping("/unschedule/{messageId}")
    public ResponseEntity<ApiResponseDto> unscheduleDirectMessage(@PathVariable Long messageId) {

        directMessageService.unschedule(messageId, new HashMap<>(), getCurrentOrgMember());
        return ResponseEntity.ok(new ApiResponseDto("Direct message unscheduled successfully"));
    }

    @PatchMapping("/unschedule/all/{chatId}")
    public ResponseEntity<ApiResponseDto> unscheduleAllDirectMessage(@PathVariable String chatId) {

        directMessageService.unscheduleAllMessagesInChat(chatId, new HashMap<>());
        return ResponseEntity.ok(new ApiResponseDto("All scheduled direct messages unscheduled successfully"));
    }

    @DeleteMapping("/schedule/{messageId}")
    public ResponseEntity<ApiResponseDto> deleteScheduleDirectMessage(@PathVariable Long messageId) {

        directMessageService.deleteSchedule(messageId);
        return ResponseEntity.ok(new ApiResponseDto("Direct message unscheduled successfully"));
    }

    @DeleteMapping("/schedule/all/{chatId}")
    public ResponseEntity<ApiResponseDto> deleteAllScheduledDirectMessages(@PathVariable String chatId) {

        directMessageService.deleteAllScheduledMessages(chatId);
        return ResponseEntity.ok(new ApiResponseDto("All scheduled direct messages deleted successfully"));
    }

    @GetMapping("/{chatId}/scheduled-messages")
    public ResponseEntity<ApiResponseDto> getScheduledDirectMessage(@PathVariable String chatId) {

        List<MessageDto> scheduledMessages = directMessageService.getScheduledMessages(chatId);
        return ResponseEntity.ok(new ApiResponseDto("Scheduled messages retrieved successfully", scheduledMessages));
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
