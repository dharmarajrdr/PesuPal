package com.pesupal.server.model.module.relation;

import com.pesupal.server.enums.CountryCode;
import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class RecordPhoneRelation extends BaseModel {

    @ManyToOne
    private ModuleRecord record;

    @OneToOne
    private ModuleField field;

    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;

    @Column(nullable = false)
    private String number;
}
