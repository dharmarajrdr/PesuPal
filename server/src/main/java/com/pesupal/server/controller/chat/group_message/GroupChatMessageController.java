package com.pesupal.server.controller.chat.group_message;

import com.pesupal.server.dto.request.chat.RescheduleMessageDto;
import com.pesupal.server.dto.request.chat.direct_message.ChatMessageDto;
import com.pesupal.server.dto.request.chat.group_message.GetGroupConversationDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.chat.MessageDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import com.pesupal.server.service.interfaces.chat.group_message.GroupChatMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/group-chat-message")
public class GroupChatMessageController extends CurrentValueRetriever {

    private final GroupChatMessageService groupChatMessageService;

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponseDto> deleteGroupMessage(@PathVariable Long messageId) {

        groupChatMessageService.deleteGroupMessage(messageId);
        return ResponseEntity.ok().body(new ApiResponseDto("Group message deleted successfully"));
    }

    @DeleteMapping("/clear/{groupId}")
    public ResponseEntity<ApiResponseDto> clearGroupChatMessages(@PathVariable String groupId) {

        groupChatMessageService.clearGroupChatMessages(groupId);
        return ResponseEntity.ok().body(new ApiResponseDto("Group chat messages cleared successfully"));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<ApiResponseDto> getGroupChatMessages(@PathVariable String groupId, @RequestParam Integer page, @RequestParam Integer size, @RequestParam(name = "pivot_message_id", required = false) Long pivotMessageId) {

        GetGroupConversationDto getGroupConversationDto = new GetGroupConversationDto(groupId, pivotMessageId, page, size);
        List<MessageDto> messageDtos = groupChatMessageService.getGroupChatMessages(getGroupConversationDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Group chat messages retrieved successfully", messageDtos));
    }

    @GetMapping("/{groupId}/scheduled-messages")
    public ResponseEntity<ApiResponseDto> getGroupChatMessages(@PathVariable String groupId) {

        List<MessageDto> messageDtos = groupChatMessageService.getScheduledMessages(groupId);
        return ResponseEntity.ok().body(new ApiResponseDto("Group chat scheduled messages retrieved successfully", messageDtos));
    }

    @PutMapping("/{groupId}/read-all")
    public ResponseEntity<ApiResponseDto> markAllGroupMessagesAsRead(@PathVariable String groupId) {

        groupChatMessageService.markAllGroupMessagesAsRead(groupId);
        return ResponseEntity.ok().body(new ApiResponseDto("All group messages marked as read successfully"));
    }

    @PostMapping("/schedule")
    public ResponseEntity<ApiResponseDto> scheduleGroupChatMessage(@RequestBody ChatMessageDto<GroupChatMessage> chatMessageDto) {

        groupChatMessageService.schedule(chatMessageDto);
        return ResponseEntity.ok(new ApiResponseDto("Message scheduled successfully"));
    }

    @PatchMapping("/reschedule/{messageId}")
    public ResponseEntity<ApiResponseDto> rescheduleGroupChatMessage(@PathVariable Long messageId, @RequestBody RescheduleMessageDto rescheduleMessageDto) {

        groupChatMessageService.reschedule(messageId, rescheduleMessageDto);
        return ResponseEntity.ok(new ApiResponseDto("Message rescheduled successfully"));
    }

    @PatchMapping("/unschedule/{messageId}")
    public ResponseEntity<ApiResponseDto> unscheduleGroupChatMessage(@PathVariable Long messageId) {

        groupChatMessageService.unschedule(messageId, new HashMap<>(), getCurrentOrgMember());
        return ResponseEntity.ok(new ApiResponseDto("Message unscheduled successfully"));
    }

    @DeleteMapping("/schedule/{messageId}")
    public ResponseEntity<ApiResponseDto> deleteScheduleDirectMessage(@PathVariable Long messageId) {

        groupChatMessageService.deleteSchedule(messageId);
        return ResponseEntity.ok(new ApiResponseDto("Direct message unscheduled successfully"));
    }

    @DeleteMapping("/schedule/all/{groupId}")
    public ResponseEntity<ApiResponseDto> deleteAllScheduledGroupChatMessages(@PathVariable String groupId) {

        groupChatMessageService.deleteAllScheduledMessages(groupId);
        return ResponseEntity.ok(new ApiResponseDto("All scheduled group chat messages deleted successfully"));
    }
}
