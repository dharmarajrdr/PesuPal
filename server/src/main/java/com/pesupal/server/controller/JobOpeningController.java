package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreateJobOpeningDto;
import com.pesupal.server.dto.request.JobOpeningFilterDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.JobOpeningDto;
import com.pesupal.server.enums.JobOpeningStatus;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.JobOpeningService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/job-opening")
public class JobOpeningController extends CurrentValueRetriever {

    private final JobOpeningService jobOpeningService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createJobOpening(@RequestBody CreateJobOpeningDto createJobOpeningDto) {

        JobOpeningDto jobOpening = jobOpeningService.createJobOpening(createJobOpeningDto);
        return ResponseEntity.ok(new ApiResponseDto("Job opening created successfully", jobOpening));
    }

    @GetMapping("/{jobOpeningId}")
    public ResponseEntity<ApiResponseDto> getJobOpeningById(@PathVariable Long jobOpeningId) {

        JobOpeningDto jobOpening = jobOpeningService.getJobOpeningDtoById(jobOpeningId);
        return ResponseEntity.ok(new ApiResponseDto("Job opening retrieved successfully", jobOpening));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getAllJobOpeningsByOrgId(
            @RequestParam(required = false) JobOpeningStatus openingStatus
    ) {

        JobOpeningFilterDto jobOpeningFilterDto = new JobOpeningFilterDto(openingStatus);
        List<JobOpeningDto> jobOpenings = jobOpeningService.getAllJobOpeningsByOrgId(getCurrentUserId(), getCurrentOrgId(), jobOpeningFilterDto);
        return ResponseEntity.ok(new ApiResponseDto("Job openings retrieved successfully", jobOpenings));
    }
}
