package com.pesupal.server.model.workdrive;

import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class StarredFile extends BaseModel {

    @ManyToOne
    private File file;

    @ManyToOne
    private OrgMember user;
}
