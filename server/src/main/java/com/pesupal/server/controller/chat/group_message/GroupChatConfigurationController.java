package com.pesupal.server.controller.chat.group_message;

import com.pesupal.server.dto.request.chat.group_message.UpdateGroupChatConfigurationDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.chat.group_message.GroupChatPermissionDto;
import com.pesupal.server.service.interfaces.chat.group_message.GroupChatConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/group-chat-configuration")
public class GroupChatConfigurationController {

    private final GroupChatConfigurationService groupChatConfigurationService;

    @PatchMapping("")
    public ResponseEntity<ApiResponseDto> updateGroupChatConfiguration(@RequestBody UpdateGroupChatConfigurationDto updateGroupChatConfigurationDto) {

        groupChatConfigurationService.updateGroupChatConfiguration(updateGroupChatConfigurationDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Group chat configuration updated successfully"));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<ApiResponseDto> getGroupPermissions(@PathVariable String groupId) {

        List<GroupChatPermissionDto> permissions = groupChatConfigurationService.getGroupPermissions(groupId);
        return ResponseEntity.ok(new ApiResponseDto("Group permissions retrieved successfully", permissions));
    }
}
