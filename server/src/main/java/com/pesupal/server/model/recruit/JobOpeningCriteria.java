package com.pesupal.server.model.recruit;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class JobOpeningCriteria {

    @ManyToOne
    private JobOpening jobOpening;

    private String title;

    private String expectation;
}
