package com.pesupal.server.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class PollOption extends BaseModel {

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Poll poll;

    @Column(nullable = false)
    private String option;

    @OneToMany(mappedBy = "pollOption", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PollVoter> voters;
}
