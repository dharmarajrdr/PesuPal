package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateCandidateDto;
import com.pesupal.server.dto.request.CreateReferralDto;
import com.pesupal.server.dto.response.CandidateDto;
import com.pesupal.server.enums.JobApplicationStatus;
import com.pesupal.server.repository.CandidateRepository;
import com.pesupal.server.service.interfaces.CandidateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

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

    }
}
