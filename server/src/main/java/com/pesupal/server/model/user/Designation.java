package com.pesupal.server.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.org.Org;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Designation extends BaseModel {

    @ManyToOne
    @JsonIgnore
    private Org org;

    @Column(nullable = false)
    private String name;

    private Long seniorityLevel;
}


