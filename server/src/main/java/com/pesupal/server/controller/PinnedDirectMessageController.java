package com.pesupal.server.controller;

import com.pesupal.server.dto.request.PinnedDirectMessageDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.PinnedDirectMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/pinned-direct-messages")
public class PinnedDirectMessageController extends CurrentValueRetriever {

    private final PinnedDirectMessageService pinnedDirectMessageService;

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getAllPinnedDirectMessages() {

        List<PinnedDirectMessageDto> pinnedDirectMessageDtos = pinnedDirectMessageService.getAllPinnedDirectMessages(getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Pinned direct messages retrieved successfully", pinnedDirectMessageDtos));
    }
}
