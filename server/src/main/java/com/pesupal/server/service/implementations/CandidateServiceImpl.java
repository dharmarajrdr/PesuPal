package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateCandidateDto;
import com.pesupal.server.dto.request.CreateCandidateTimelineDto;
import com.pesupal.server.dto.request.CreateReferralDto;
import com.pesupal.server.dto.response.CandidateDto;
import com.pesupal.server.enums.JobApplicationStatus;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.recruit.Candidate;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.CandidateRepository;
import com.pesupal.server.service.interfaces.CandidateService;
import com.pesupal.server.service.interfaces.CandidateTimelineService;
import com.pesupal.server.service.interfaces.OrgMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final OrgMemberService orgMemberService;
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
        return null;
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

        List<String> eligibleRoles = List.of("HR", "Human Resource", "Recruiter");

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        if (!eligibleRoles.contains(orgMember.getDesignation().getName())) {
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
