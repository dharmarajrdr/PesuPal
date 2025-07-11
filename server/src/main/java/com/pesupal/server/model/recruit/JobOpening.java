package com.pesupal.server.model.recruit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.enums.JobOpeningStatus;
import com.pesupal.server.enums.JobType;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class JobOpening extends CreationTimeAuditable {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    private Long budget;

    @Column(nullable = false)
    private LocalDateTime openTill;

    @ManyToOne
    @JsonIgnore
    private User hiringManager;

    @Enumerated(EnumType.STRING)
    private JobOpeningStatus status;

    private int positionsAvailable;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @ElementCollection
    private List<JobOpeningCriteria> criteria;

    @OneToMany(mappedBy = "jobOpening", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Candidate> candidates;
}
