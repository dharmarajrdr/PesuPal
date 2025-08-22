package com.pesupal.server.model.post;

import com.pesupal.server.enums.Visibility;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Poll extends BaseModel {

    @Column(nullable = false)
    private String question;

    @Enumerated(EnumType.STRING)
    private Visibility votersVisibility;

    @Column(nullable = false)
    private Boolean votesUpdatable;

    @OneToOne(cascade = CascadeType.ALL)
    private Post post;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PollOption> options;
}
