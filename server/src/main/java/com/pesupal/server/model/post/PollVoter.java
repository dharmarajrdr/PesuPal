package com.pesupal.server.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class PollVoter extends BaseModel {

    @ManyToOne
    @JsonIgnore
    private OrgMember voter;

    @ManyToOne
    @JsonIgnore
    private PollOption pollOption;
}
