package com.pesupal.server.model.recruit;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class JobOpeningCriteria {

    private String title;

    private String expectation;
}
