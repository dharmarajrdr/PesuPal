package com.pesupal.server.controller;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.CandidateTimelineService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/candidate-timeline")
public class CandidateTimelineController extends CurrentValueRetriever {

    private final CandidateTimelineService candidateTimelineService;

    @GetMapping("/{candidateId}")
    public ResponseEntity<ApiResponseDto> getCandidateTimeline(@PathVariable Long candidateId) {

        var timelineEvents = candidateTimelineService.getTimelineByCandidateIdAndJobId(candidateId, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok(new ApiResponseDto("Candidate timeline retrieved successfully", timelineEvents));
    }
}
