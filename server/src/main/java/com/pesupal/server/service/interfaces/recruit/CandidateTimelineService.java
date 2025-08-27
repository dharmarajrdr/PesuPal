package com.pesupal.server.service.interfaces.recruit;

import com.pesupal.server.dto.request.recruit.CreateCandidateTimelineDto;
import com.pesupal.server.dto.response.recruit.CandidateTimelineDto;

import java.util.List;

public interface CandidateTimelineService {

    void createCandidateTimeline(CreateCandidateTimelineDto createCandidateTimelineDto);

    List<CandidateTimelineDto> getTimelineByCandidateIdAndJobId(Long candidateId, Long userId, Long orgId);
}
