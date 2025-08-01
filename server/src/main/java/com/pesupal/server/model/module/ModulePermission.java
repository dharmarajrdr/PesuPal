package com.pesupal.server.model.module;

import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class ModulePermission extends BaseModel {

    @ManyToOne
    private Module module;

    @Enumerated(EnumType.STRING)
    private ModuleRole role;

    private boolean createRecord;

    private boolean readRecord;

    private boolean deleteRecord;

    private boolean manageMembers;
}
