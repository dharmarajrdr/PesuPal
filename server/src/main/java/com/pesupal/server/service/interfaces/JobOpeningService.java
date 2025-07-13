package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateJobOpeningDto;
import com.pesupal.server.dto.request.JobOpeningFilterDto;
import com.pesupal.server.dto.response.JobOpeningDto;
import com.pesupal.server.model.recruit.JobOpening;

import java.util.List;

public interface JobOpeningService {

    JobOpeningDto createJobOpening(CreateJobOpeningDto createJobOpeningDto, Long userId, Long orgId);

    JobOpening getJobOpeningById(Long jobOpeningId);

    JobOpeningDto getJobOpeningDtoById(Long jobOpeningId);

    List<JobOpeningDto> getAllJobOpeningsByOrgId(Long orgId, JobOpeningFilterDto jobOpeningFilterDto);
}
