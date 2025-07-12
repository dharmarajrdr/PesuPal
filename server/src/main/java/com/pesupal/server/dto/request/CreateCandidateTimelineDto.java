package com.pesupal.server.dto.request;

import com.pesupal.server.model.recruit.Candidate;
import com.pesupal.server.model.recruit.CandidateTimeline;
import lombok.Data;

@Data
public class CreateCandidateTimelineDto {

    private Candidate candidate;

    private String description;

    public CandidateTimeline toCandidateTimeline() {

        CandidateTimeline candidateTimeline = new CandidateTimeline();
        candidateTimeline.setCandidate(this.candidate);
        candidateTimeline.setDescription(this.description);
        return candidateTimeline;
    }
}
