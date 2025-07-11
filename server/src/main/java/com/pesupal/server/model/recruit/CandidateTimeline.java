package com.pesupal.server.model.recruit;

import com.pesupal.server.model.CreationTimeAuditable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class CandidateTimeline extends CreationTimeAuditable {

    @ManyToOne
    private Candidate candidate;

    private String description;
}
