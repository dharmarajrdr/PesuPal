package com.pesupal.server.model.workdrive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class FileAccessStat extends CreationTimeAuditable {

    @ManyToOne
    @JsonIgnore
    private File file;

    @ManyToOne
    @JsonIgnore
    private OrgMember accessedBy;
}
