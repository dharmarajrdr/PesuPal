package com.pesupal.server.model.module.relation;

import com.pesupal.server.enums.Day;
import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class RecordDayRelation extends BaseModel {

    @ManyToOne
    private ModuleRecord record;

    @ManyToOne
    private ModuleField field;

    @Enumerated(EnumType.STRING)
    private Day day;
}
