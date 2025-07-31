package com.pesupal.server.model.module;

import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ModulePermission extends BaseModel {

    @ManyToOne
    private Module module;

    @Enumerated
    private ModuleRole role;

    private boolean createRecord;

    private boolean readRecord;

    private boolean deleteRecord;

    private boolean manageMembers;
}
