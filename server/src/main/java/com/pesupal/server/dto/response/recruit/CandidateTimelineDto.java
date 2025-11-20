package com.pesupal.server.dto.response.recruit;

import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.model.recruit.CandidateTimeline;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CandidateTimelineDto {

    private UserBasicInfoDto createdBy;

    private LocalDateTime createdAt;

    private String description;

    public static CandidateTimelineDto fromCandidateTimeline(CandidateTimeline candidateTimeline) {

        CandidateTimelineDto dto = new CandidateTimelineDto();
        dto.setCreatedAt(candidateTimeline.getCreatedAt());
        dto.setDescription(candidateTimeline.getDescription());
        return dto;
    }
}
