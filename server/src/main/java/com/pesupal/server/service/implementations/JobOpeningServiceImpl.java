package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateJobOpeningDto;
import com.pesupal.server.dto.response.JobOpeningDto;
import com.pesupal.server.repository.JobOpeningRepository;
import com.pesupal.server.service.interfaces.JobOpeningService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JobOpeningServiceImpl implements JobOpeningService {

    private final JobOpeningRepository jobOpeningRepository;

    /**
     * Create a new job opening.
     *
     * @param createJobOpeningDto
     * @return JobOpeningDto
     */
    @Override
    public JobOpeningDto createJobOpening(CreateJobOpeningDto createJobOpeningDto) {
        return null;
    }
}
