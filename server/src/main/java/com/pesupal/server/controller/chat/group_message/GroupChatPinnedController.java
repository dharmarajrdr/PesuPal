package com.pesupal.server.controller.chat.group_message;

import com.pesupal.server.dto.request.chat.direct_message.PinnedChatDto;
import com.pesupal.server.dto.request.chat.group_message.CreatePinGroupChatMessageDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.service.interfaces.chat.group_message.GroupChatPinnedService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/pinned-group-messages")
public class GroupChatPinnedController {

    private final GroupChatPinnedService groupChatPinnedService;

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getAllPinnedGroupMessages() {

        List<PinnedChatDto> pinnedGroupChatMessageDtos = groupChatPinnedService.getAllPinnedGroupChatMessages();
        return ResponseEntity.ok().body(new ApiResponseDto("Pinned direct messages retrieved successfully", pinnedGroupChatMessageDtos));
    }

    @PostMapping("/pin")
    public ResponseEntity<ApiResponseDto> pinGroupChatMessage(@RequestBody CreatePinGroupChatMessageDto createPinGroupChatMessageDto) {

        PinnedChatDto pinnedGroupChatMessageDto = groupChatPinnedService.pinGroupChatMessage(createPinGroupChatMessageDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Group chat pinned successfully", pinnedGroupChatMessageDto));
    }

    @DeleteMapping("/pin/{id}")
    public ResponseEntity<ApiResponseDto> unpinGroupChatMessage(@PathVariable Long id) {

        groupChatPinnedService.unpinGroupChatMessage(id);
        return ResponseEntity.ok().body(new ApiResponseDto("Group chat unpinned successfully", null));
    }
}