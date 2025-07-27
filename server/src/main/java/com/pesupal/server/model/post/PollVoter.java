package com.pesupal.server.model.post;

import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class PollVoter extends BaseModel {

    @ManyToOne
    private OrgMember voter;

    @ManyToOne
    private PollOption pollOption;
}
