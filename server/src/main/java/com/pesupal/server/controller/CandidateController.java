package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreateCandidateDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.CandidateDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.CandidateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/candidate")
public class CandidateController extends CurrentValueRetriever {

    private final CandidateService candidateService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createCandidate(@RequestBody CreateCandidateDto createCandidateDto) {

        CandidateDto candidateDto = candidateService.createCandidate(createCandidateDto);
        return ResponseEntity.ok(new ApiResponseDto("Candidate created successfully", candidateDto));
    }

}
