package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.recruit.CreateJobOpeningDto;
import com.pesupal.server.dto.request.recruit.JobOpeningFilterDto;
import com.pesupal.server.dto.response.recruit.JobOpeningDto;
import com.pesupal.server.model.recruit.JobOpening;

import java.util.List;

public interface JobOpeningService {

    JobOpeningDto createJobOpening(CreateJobOpeningDto createJobOpeningDto);

    JobOpening getJobOpeningById(Long jobOpeningId);

    JobOpeningDto getJobOpeningDtoById(Long jobOpeningId);

    List<JobOpeningDto> getAllJobOpeningsByOrgId(JobOpeningFilterDto jobOpeningFilterDto);
}
