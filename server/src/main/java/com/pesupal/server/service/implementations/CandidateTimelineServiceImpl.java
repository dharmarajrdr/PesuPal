package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateCandidateTimelineDto;
import com.pesupal.server.dto.response.CandidateTimelineDto;
import com.pesupal.server.service.interfaces.CandidateTimelineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CandidateTimelineServiceImpl implements CandidateTimelineService {

    /**
     * Creates a new candidate timeline entry.
     *
     * @param createCandidateTimelineDto
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public CandidateTimelineDto createCandidateTimeline(CreateCandidateTimelineDto createCandidateTimelineDto, Long userId, Long orgId) {
        return null;
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
    public List<CandidateTimelineDto> getTimelineByCandidateIdAndJobId(Long candidateId, Long jobId, Long userId, Long orgId) {
        return List.of();
    }
}
