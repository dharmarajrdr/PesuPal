package com.pesupal.server.service.implementations;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.dto.request.CreateCandidateTimelineDto;
import com.pesupal.server.dto.response.CandidateTimelineDto;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.recruit.Candidate;
import com.pesupal.server.model.recruit.CandidateTimeline;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.CandidateTimelineRepository;
import com.pesupal.server.service.interfaces.CandidateService;
import com.pesupal.server.service.interfaces.CandidateTimelineService;
import com.pesupal.server.service.interfaces.OrgMemberService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CandidateTimelineServiceImpl implements CandidateTimelineService {

    private final OrgMemberService orgMemberService;
    private final CandidateService candidateService;
    private final CandidateTimelineRepository candidateTimelineRepository;

    public CandidateTimelineServiceImpl(OrgMemberService orgMemberService, @Lazy CandidateService candidateService, CandidateTimelineRepository candidateTimelineRepository) {
        this.orgMemberService = orgMemberService;
        this.candidateService = candidateService;
        this.candidateTimelineRepository = candidateTimelineRepository;
    }

    /**
     * Creates a new candidate timeline entry.
     *
     * @param createCandidateTimelineDto
     * @return
     */
    @Override
    public void createCandidateTimeline(CreateCandidateTimelineDto createCandidateTimelineDto) {

        CandidateTimeline candidateTimeline = createCandidateTimelineDto.toCandidateTimeline();
        candidateTimelineRepository.save(candidateTimeline);
    }

    /**
     * Retrieves the timeline of a candidate by their ID.
     *
     * @param candidateId
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public List<CandidateTimelineDto> getTimelineByCandidateIdAndJobId(Long candidateId, Long userId, Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        if (!StaticConfig.HUMAN_RESOURCE_ROLES.contains(orgMember.getDesignation().getName())) {
            throw new PermissionDeniedException("You do not have permission to view the candidate timeline.");
        }

        Candidate candidate = candidateService.getCandidateById(candidateId);
        Map<Long, OrgMember> memo = new HashMap<>();

        return candidateTimelineRepository.findAllByCandidateOrderByCreatedAtDesc(candidate)
                .stream()
                .map(candidateTimeline -> {
                    OrgMember createdByOrgMember = memo.computeIfAbsent(candidateTimeline.getCreatedBy().getId(), id -> orgMemberService.getOrgMemberByUserIdAndOrgId(id, orgId));
                    return CandidateTimelineDto.fromCandidateTimelineAndOrgMember(candidateTimeline, createdByOrgMember);
                })
                .toList();
    }
}
