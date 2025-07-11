package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateCandidateTimelineDto;
import com.pesupal.server.dto.response.CandidateTimelineDto;

import java.util.List;

public interface CandidateTimelineService {

    CandidateTimelineDto createCandidateTimeline(CreateCandidateTimelineDto createCandidateTimelineDto, Long userId, Long orgId);

    List<CandidateTimelineDto> getTimelineByCandidateIdAndJobId(Long candidateId, Long jobId, Long userId, Long orgId);
}
