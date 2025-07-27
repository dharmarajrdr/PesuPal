package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreatePinDirectMessageDto;
import com.pesupal.server.dto.request.PinnedChatDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.PinnedDirectMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/pinned-direct-messages")
public class PinnedDirectMessageController extends CurrentValueRetriever {

    private final PinnedDirectMessageService pinnedDirectMessageService;

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getAllPinnedDirectMessages() {

        List<PinnedChatDto> pinnedDirectMessageDtos = pinnedDirectMessageService.getAllPinnedDirectMessages();
        return ResponseEntity.ok().body(new ApiResponseDto("Pinned direct messages retrieved successfully", pinnedDirectMessageDtos));
    }

    @PostMapping("/pin")
    public ResponseEntity<ApiResponseDto> pinDirectMessage(@RequestBody CreatePinDirectMessageDto createPinDirectMessageDto) {

        PinnedChatDto pinnedDirectMessageDto = pinnedDirectMessageService.pinDirectMessage(createPinDirectMessageDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Direct message pinned successfully", pinnedDirectMessageDto));
    }

    @DeleteMapping("/pin/{id}")
    public ResponseEntity<ApiResponseDto> unpinDirectMessage(@PathVariable Long id) {

        pinnedDirectMessageService.unpinDirectMessage(id, getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Direct message unpinned successfully", null));
    }
}
