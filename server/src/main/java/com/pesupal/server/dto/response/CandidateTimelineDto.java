package com.pesupal.server.dto.response;

import com.pesupal.server.model.recruit.CandidateTimeline;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CandidateTimelineDto {

    private UserBasicInfoDto createdBy;

    private LocalDateTime createdAt;

    private String description;

    public static CandidateTimelineDto fromCandidateTimelineAndOrgMember(CandidateTimeline candidateTimeline, OrgMember orgMember) {

        CandidateTimelineDto dto = new CandidateTimelineDto();
        dto.setCreatedAt(candidateTimeline.getCreatedAt());
        dto.setDescription(candidateTimeline.getDescription());
        dto.setCreatedBy(UserBasicInfoDto.fromOrgMember(orgMember));
        return dto;
    }
}
