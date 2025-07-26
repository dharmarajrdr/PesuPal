package com.pesupal.server.controller.group;

import com.pesupal.server.dto.request.group.UpdateGroupChatConfigurationDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.group.GroupChatConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/group-chat-configuration")
public class GroupChatConfigurationController extends CurrentValueRetriever {

    private final GroupChatConfigurationService groupChatConfigurationService;

    @PatchMapping("")
    public ResponseEntity<ApiResponseDto> updateGroupChatConfiguration(@RequestBody UpdateGroupChatConfigurationDto updateGroupChatConfigurationDto) {

        groupChatConfigurationService.updateGroupChatConfiguration(updateGroupChatConfigurationDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Group chat configuration updated successfully"));
    }
}
