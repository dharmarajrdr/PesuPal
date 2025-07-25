package com.pesupal.server.controller.group;

import com.pesupal.server.dto.request.PinnedChatDto;
import com.pesupal.server.dto.request.group.CreatePinGroupChatMessageDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.group.GroupChatPinnedService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/pinned-group-messages")
public class GroupChatPinnedController extends CurrentValueRetriever {

    private final GroupChatPinnedService groupChatPinnedService;

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getAllPinnedGroupMessages() {

        List<PinnedChatDto> pinnedGroupChatMessageDtos = groupChatPinnedService.getAllPinnedGroupChatMessages(getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Pinned direct messages retrieved successfully", pinnedGroupChatMessageDtos));
    }

    @PostMapping("/pin")
    public ResponseEntity<ApiResponseDto> pinGroupChatMessage(@RequestBody CreatePinGroupChatMessageDto createPinGroupChatMessageDto) {

        PinnedChatDto pinnedGroupChatMessageDto = groupChatPinnedService.pinGroupChatMessage(createPinGroupChatMessageDto, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Direct message pinned successfully", pinnedGroupChatMessageDto));
    }

    @DeleteMapping("/pin/{id}")
    public ResponseEntity<ApiResponseDto> unpinGroupChatMessage(@PathVariable Long id) {

        groupChatPinnedService.unpinGroupChatMessage(id, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Direct message unpinned successfully", null));
    }
}