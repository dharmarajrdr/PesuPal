package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreatePollVoterDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.PollDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.PollVoterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/post/poll")
public class PollVoterController extends CurrentValueRetriever {

    private final PollVoterService pollVoterService;

    @PostMapping("/vote")
    public ResponseEntity<ApiResponseDto> createPollVoter(@RequestBody CreatePollVoterDto createPollVoterDto) {

        PollDto pollDto = pollVoterService.createPollVoter(createPollVoterDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Vote recorded successfully", pollDto));
    }
}
