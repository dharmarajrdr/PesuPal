package com.pesupal.server.model.module.relation;

import com.pesupal.server.enums.Currency;
import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class RecordCurrencyRelation extends BaseModel {

    @ManyToOne
    private ModuleRecord record;

    @ManyToOne
    private ModuleField field;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}
