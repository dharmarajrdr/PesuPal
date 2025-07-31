package com.pesupal.server.model.module.relation;

import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.Transition;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class RecordTransitionRelation extends BaseModel {

    @ManyToOne
    private ModuleRecord record;

    @OneToOne
    private ModuleField field;

    @ManyToOne
    private Transition transition;
}
