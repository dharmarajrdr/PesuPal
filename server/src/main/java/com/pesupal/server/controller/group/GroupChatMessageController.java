package com.pesupal.server.controller.group;

import com.pesupal.server.dto.request.GetGroupConversationDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.MessageDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.group.GroupChatMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/group-chat-message")
public class GroupChatMessageController extends CurrentValueRetriever {

    private final GroupChatMessageService groupChatMessageService;

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponseDto> deleteGroupMessage(@PathVariable Long messageId) {

        groupChatMessageService.deleteGroupMessage(messageId, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Group message deleted successfully"));
    }

    @DeleteMapping("/clear/{groupId}")
    public ResponseEntity<ApiResponseDto> clearGroupChatMessages(@PathVariable Long groupId) {

        groupChatMessageService.clearGroupChatMessages(groupId, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Group chat messages cleared successfully"));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<ApiResponseDto> getGroupChatMessages(@PathVariable String groupId, @RequestParam Integer page, @RequestParam Integer size, @RequestParam(name = "pivot_message_id", required = false) Long pivotMessageId) {

        GetGroupConversationDto getGroupConversationDto = new GetGroupConversationDto(groupId, pivotMessageId, page, size);
        List<MessageDto> messageDtos = groupChatMessageService.getGroupChatMessages(getGroupConversationDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Group chat messages retrieved successfully", messageDtos));
    }

    @PutMapping("/{groupId}/read-all")
    public ResponseEntity<ApiResponseDto> markAllGroupMessagesAsRead(@PathVariable Long groupId) {

        groupChatMessageService.markAllGroupMessagesAsRead(groupId, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("All group messages marked as read successfully"));
    }
}
