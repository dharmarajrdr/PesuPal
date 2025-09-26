package com.pesupal.server.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = false)
public class PollOption extends BaseModel {

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Poll poll;

    @Column(nullable = false)
    private String option;

    @OneToMany(mappedBy = "pollOption", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PollVoter> voters = new ArrayList<>();
}
