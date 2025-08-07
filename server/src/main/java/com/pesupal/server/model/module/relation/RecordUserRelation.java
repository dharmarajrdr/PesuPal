package com.pesupal.server.model.module.relation;

import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RecordUserRelation extends BaseModel {

    @ManyToOne
    private ModuleRecord record;

    @ManyToOne
    private ModuleField field;

    @ManyToOne
    private OrgMember user;
}
