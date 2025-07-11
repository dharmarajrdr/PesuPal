package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateCandidateDto;
import com.pesupal.server.dto.request.CreateReferralDto;
import com.pesupal.server.dto.response.CandidateDto;
import com.pesupal.server.enums.JobApplicationStatus;

import java.util.List;

public interface CandidateService {

    CandidateDto createCandidate(CreateCandidateDto createCandidateDto, Long userId, Long orgId);

    List<CandidateDto> getAllCandidatesAppliedForJobOpening(Long jobOpeningId, Long userId, Long orgId);

    CandidateDto createReferral(CreateReferralDto createReferralDto, Long userId, Long orgId);

    void updateCandidateStatus(Long candidateId, JobApplicationStatus status, Long userId, Long orgId);
}
