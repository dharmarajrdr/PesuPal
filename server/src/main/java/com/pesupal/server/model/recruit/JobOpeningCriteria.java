package com.pesupal.server.model.recruit;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class JobOpeningCriteria {

    private String title;

    private String expectation;
}
