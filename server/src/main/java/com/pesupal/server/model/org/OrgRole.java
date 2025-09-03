package com.pesupal.server.model.org;

import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class OrgRole extends BaseModel {

    @ManyToOne
    private OrgMember createdBy;

    private String name;
}
