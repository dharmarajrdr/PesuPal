package com.pesupal.server.model.module;

import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Transition extends BaseModel {

    @ManyToOne
    private ModuleField field;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private String colorCode;
}
