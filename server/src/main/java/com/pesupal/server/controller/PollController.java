package com.pesupal.server.controller;

import com.pesupal.server.dto.request.UpdatePollDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.PollService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/post/poll")
public class PollController extends CurrentValueRetriever {

    private final PollService pollService;

    @PatchMapping("/{pollId}")
    public ResponseEntity<ApiResponseDto> updatePoll(@PathVariable Long pollId, @RequestBody UpdatePollDto updatePollDto) {

        pollService.updatePoll(pollId, updatePollDto, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Poll updated successfully", null));
    }
}
