package com.pesupal.server.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.enums.Visibility;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = false)
public class Poll extends BaseModel {

    @Column(nullable = false)
    private String question;

    @Enumerated(EnumType.STRING)
    private Visibility votersVisibility;

    @Column(nullable = false)
    private Boolean votesUpdatable;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Post post;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PollOption> options;
}
