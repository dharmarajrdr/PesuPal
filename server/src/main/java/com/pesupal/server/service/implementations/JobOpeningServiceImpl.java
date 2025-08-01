package com.pesupal.server.service.implementations;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.dto.request.CreateJobOpeningDto;
import com.pesupal.server.dto.request.JobOpeningFilterDto;
import com.pesupal.server.dto.response.JobOpeningDto;
import com.pesupal.server.enums.JobOpeningStatus;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.recruit.JobOpening;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.JobOpeningRepository;
import com.pesupal.server.service.interfaces.JobOpeningService;
import com.pesupal.server.service.interfaces.OrgMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobOpeningServiceImpl extends CurrentValueRetriever implements JobOpeningService {

    private final JobOpeningRepository jobOpeningRepository;
    private final OrgMemberService orgMemberService;

    /**
     * Create a new job opening.
     *
     * @param createJobOpeningDto
     * @return JobOpeningDto
     */
    @Override
    public JobOpeningDto createJobOpening(CreateJobOpeningDto createJobOpeningDto) {

        OrgMember hiringManager = getCurrentOrgMember();

        if (!StaticConfig.HUMAN_RESOURCE_ROLES.contains(hiringManager.getDesignation().getName())) {
            throw new PermissionDeniedException("You do not have permission to create a job opening.");
        }

        JobOpening jobOpening = createJobOpeningDto.toJobOpening();
        jobOpening.setHiringManager(hiringManager);
        jobOpening.setOrg(hiringManager.getOrg());
        jobOpening.setStatus(JobOpeningStatus.OPEN);
        return JobOpeningDto.fromJobOpening(jobOpeningRepository.save(jobOpening));
    }

    /**
     * Retrieve a job opening by its ID.
     *
     * @param jobOpeningId
     * @return
     */
    public JobOpening getJobOpeningById(Long jobOpeningId) {

        return jobOpeningRepository.findById(jobOpeningId).orElseThrow(() -> new DataNotFoundException("Job opening not found with ID: " + jobOpeningId));
    }

    /**
     * Retrieve a job opening by its ID and organization ID.
     *
     * @param jobOpeningId
     * @return
     */
    @Override
    public JobOpeningDto getJobOpeningDtoById(Long jobOpeningId) {

        return JobOpeningDto.fromJobOpening(getJobOpeningById(jobOpeningId));
    }

    /**
     * Retrieve all job openings for a specific organization.
     *
     * @return
     */
    @Override
    public List<JobOpeningDto> getAllJobOpeningsByOrgId(JobOpeningFilterDto jobOpeningFilterDto) {

        OrgMember orgMember = getCurrentOrgMember();
        Long orgId = orgMember.getOrg().getId();
        JobOpeningStatus status = jobOpeningFilterDto.getStatus();
        if (!StaticConfig.HUMAN_RESOURCE_ROLES.contains(orgMember.getRole().name())) {
            throw new PermissionDeniedException("You do not have permission to view job openings.");
        }
        return jobOpeningRepository.findAllByOrgIdAndStatusOrderByCreatedAtDesc(orgId, status).stream().map(jobOpening -> JobOpeningDto.fromJobOpening(jobOpening)).toList();
    }
}
