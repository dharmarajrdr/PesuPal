package com.pesupal.server.dto.response.recruit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.enums.JobApplicationStatus;
import com.pesupal.server.model.recruit.Candidate;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandidateDto {

    private String name;

    private JobOpeningDto jobOpening;

    private UUID resumeId;

    private UserBasicInfoDto referredBy;

    private JobApplicationStatus applicationStatus;

    private List<CandidateTimelineDto> timeline;

    public static CandidateDto fromCandidate(Candidate candidate) {

        CandidateDto candidateDto = new CandidateDto();
        candidateDto.setName(candidate.getName());
        candidateDto.setResumeId(candidate.getResumeId());
        candidateDto.setApplicationStatus(candidate.getStatus());
        candidateDto.setJobOpening(JobOpeningDto.fromJobOpening(candidate.getJobOpening()));
        return candidateDto;
    }
}
