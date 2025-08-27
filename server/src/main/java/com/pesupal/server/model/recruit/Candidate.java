package com.pesupal.server.model.recruit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.enums.JobApplicationStatus;
import com.pesupal.server.model.PublicAccessModel;
import com.pesupal.server.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Candidate extends PublicAccessModel {

    @ManyToOne
    @JsonIgnore
    private User applicant;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnore
    private JobOpening jobOpening;

    @Column(nullable = false, unique = true)
    private UUID resumeId;

    @ManyToOne
    @JsonIgnore
    private User referredBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobApplicationStatus status;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CandidateTimeline> timeline;
}
