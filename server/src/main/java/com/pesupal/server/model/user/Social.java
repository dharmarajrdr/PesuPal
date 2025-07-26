package com.pesupal.server.model.user;

import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Social extends BaseModel {

    @OneToOne
    private OrgMember user;

    private String linkedin;

    private String instagram;

    private String github;

    private String twitter;
}
