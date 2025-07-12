package com.pesupal.server.service.implementations;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.dto.request.CreateCandidateDto;
import com.pesupal.server.dto.request.CreateCandidateTimelineDto;
import com.pesupal.server.dto.request.CreateReferralDto;
import com.pesupal.server.dto.response.CandidateDto;
import com.pesupal.server.enums.JobApplicationStatus;
import com.pesupal.server.enums.JobOpeningStatus;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.recruit.Candidate;
import com.pesupal.server.model.recruit.CandidateTimeline;
import com.pesupal.server.model.recruit.JobOpening;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.CandidateRepository;
import com.pesupal.server.service.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final UserService userService;
    private final OrgMemberService orgMemberService;
    private final JobOpeningService jobOpeningService;
    private final CandidateRepository candidateRepository;
    private final CandidateTimelineService candidateTimelineService;

    /**
     * Retrieves a candidate by their ID.
     *
     * @param candidateId
     * @return
     */
    @Override
    public Candidate getCandidateById(Long candidateId) {

        return candidateRepository.findById(candidateId).orElseThrow(() -> new DataNotFoundException("Candidate with ID " + candidateId + " not found."));
    }

    /**
     * Creates a new candidate for a job opening.
     *
     * @param createCandidateDto
     * @return
     */
    @Override
    public CandidateDto createCandidate(CreateCandidateDto createCandidateDto, Long userId, Long orgId) {

        Long candidateId = createCandidateDto.getUserId();

        Long referredById = null;

        if (candidateId == null) { // If userId is not provided, use the current user as the candidate
            candidateId = userId;
            if (orgMemberService.existsByUserIdAndOrgId(candidateId, orgId)) {
                throw new PermissionDeniedException("You are already a member of this organization.");
            }
        } else { // If userId is provided, then it is a referral
            if (!orgMemberService.existsByUserIdAndOrgId(userId, orgId)) {
                throw new PermissionDeniedException("You do not have permission to refer candidates for this organization.");
            }
            if (orgMemberService.existsByUserIdAndOrgId(candidateId, orgId)) {
                throw new PermissionDeniedException("Given user is already a member of this organization.");
            }
            referredById = userId;
        }

        if (candidateId.equals(referredById)) {
            throw new ActionProhibitedException("You cannot refer yourself for a job opening.");
        }

        User applicant = userService.getUserById(candidateId);

        JobOpening jobOpening = jobOpeningService.getJobOpeningById(createCandidateDto.getJobId());

        if (candidateRepository.existsByApplicantAndJobOpening(applicant, jobOpening)) {
            throw new ActionProhibitedException("You have already applied for this job opening.");
        }

        if (!jobOpening.getStatus().equals(JobOpeningStatus.PUBLISHED)) {
            throw new ActionProhibitedException("Unable to apply for job opening with ID " + createCandidateDto.getJobId() + " as the status of the job opening is '" + jobOpening.getStatus() + "'.");
        }

        Candidate candidate = createCandidateDto.toCandidate();
        candidate.setApplicant(applicant);
        candidate.setStatus(JobApplicationStatus.UNDER_REVIEW);
        candidate.setJobOpening(jobOpening);
        OrgMember referredByOrgMember = null;
        CandidateTimeline candidateTimeline = new CandidateTimeline();
        if (referredById != null) {
            User referredBy = orgMemberService.getOrgMemberByUserIdAndOrgId(referredById, orgId).getUser();
            candidate.setReferredBy(referredBy);
            referredByOrgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(referredBy.getId(), orgId);
            candidateTimeline.setCreatedBy(referredBy);
            candidateTimeline.setDescription("Referred '" + createCandidateDto.getName() + "' for job opening.'");
        } else {
            User user = userService.getUserById(candidateId);
            candidateTimeline.setCreatedBy(user);
            candidateTimeline.setDescription("Applied for job opening.");
        }
        candidate.setTimeline(List.of(candidateTimeline));
        return CandidateDto.fromCandidateAndOrgMember(candidateRepository.save(candidate), referredByOrgMember);
    }

    /**
     * Retrieves all candidates who have applied for a specific job opening.
     *
     * @param jobOpeningId
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public List<CandidateDto> getAllCandidatesAppliedForJobOpening(Long jobOpeningId, Long userId, Long orgId) {
        return List.of();
    }

    /**
     * Creates a referral for a candidate.
     *
     * @param createReferralDto
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public CandidateDto createReferral(CreateReferralDto createReferralDto, Long userId, Long orgId) {
        return null;
    }

    /**
     * Updates the status of a candidate's job application.
     *
     * @param candidateId
     * @param status
     * @param userId
     * @param orgId
     */
    @Override
    public void updateCandidateStatus(Long candidateId, JobApplicationStatus status, Long userId, Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        if (!StaticConfig.HUMAN_RESOURCE_ROLES.contains(orgMember.getDesignation().getName())) {
            throw new PermissionDeniedException("You do not have permission to update candidate status.");
        }

        Candidate candidate = getCandidateById(candidateId);
        if (candidate.getStatus().equals(status)) {
            throw new ActionProhibitedException("Candidate status is already set to " + status + ".");
        }

        candidate.setStatus(status);
        candidateRepository.save(candidate);

        CreateCandidateTimelineDto createCandidateTimelineDto = new CreateCandidateTimelineDto();
        createCandidateTimelineDto.setCandidate(candidate);
        createCandidateTimelineDto.setDescription("Status updated from " + candidate.getStatus() + " to " + status + " by " + orgMember.getDisplayName() + ".");
        candidateTimelineService.createCandidateTimeline(createCandidateTimelineDto);
    }
}
