package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateJobOpeningDto;
import com.pesupal.server.dto.response.JobOpeningDto;

public interface JobOpeningService {

    JobOpeningDto createJobOpening(CreateJobOpeningDto createJobOpeningDto);
}
