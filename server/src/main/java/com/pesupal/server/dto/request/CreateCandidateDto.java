package com.pesupal.server.dto.request;

import com.pesupal.server.model.recruit.Candidate;
import lombok.Data;

import java.util.UUID;

/**
 * If candidate created, then userId and referredById are null.
 * If candidate is referred, then userId is mandatory.
 */

@Data
public class CreateCandidateDto {

    private Long userId;

    private String name;

    private Long jobId;

    private UUID resumeId;

    public Candidate toCandidate() {

        Candidate candidate = new Candidate();
        candidate.setName(name);
        candidate.setResumeId(resumeId);
        return candidate;
    }
}
