package com.pesupal.server.model.post;

import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class PollOption extends BaseModel {

    @ManyToOne
    private Poll poll;

    @Column(nullable = false)
    private String option;

    @OneToMany(mappedBy = "pollOption")
    private List<PollVoter> voters;
}
