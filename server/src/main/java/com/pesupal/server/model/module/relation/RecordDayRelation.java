package com.pesupal.server.model.module.relation;

import com.pesupal.server.enums.Day;
import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class RecordDayRelation extends BaseModel {

    @ManyToOne
    private ModuleRecord record;

    @OneToOne
    private ModuleField field;

    @Enumerated
    private Day day;
}
