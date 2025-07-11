package com.pesupal.server.model.recruit;

import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class JobOpeningCriteria extends BaseModel {

    @ManyToOne
    private JobOpening jobOpening;

    private String title;

    private String expectation;
}
