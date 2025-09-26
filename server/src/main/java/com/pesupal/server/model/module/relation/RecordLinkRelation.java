package com.pesupal.server.model.module.relation;

import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class RecordLinkRelation extends BaseModel {

    @ManyToOne
    private ModuleRecord record;

    @ManyToOne
    private ModuleField field;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;
}
