package com.pesupal.server.controller.group;

import com.pesupal.server.dto.request.group.CreateGroupMessageDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.group.GroupMessageDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.group.GroupChatMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/group-chat-message")
public class GroupChatMessageController extends CurrentValueRetriever {

    private final GroupChatMessageService groupChatMessageService;

    @PostMapping("")
    public ResponseEntity<ApiResponseDto> postmessageInGroup(@RequestBody CreateGroupMessageDto createGroupMessageDto) {

        GroupMessageDto groupMessageDto = groupChatMessageService.postMessageInGroup(createGroupMessageDto, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Group message posted successfully", groupMessageDto));
    }
}
