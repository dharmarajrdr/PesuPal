package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateCandidateTimelineDto;
import com.pesupal.server.dto.response.CandidateTimelineDto;

import java.util.List;

public interface CandidateTimelineService {

    void createCandidateTimeline(CreateCandidateTimelineDto createCandidateTimelineDto);

    List<CandidateTimelineDto> getTimelineByCandidateIdAndJobId(Long candidateId, Long jobId, Long userId, Long orgId);
}
