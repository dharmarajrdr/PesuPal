package com.pesupal.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subscription {

    @Id
    private Long subscriptionId;

    private String title;

    private Float monthlyPrice;

    private Float yearlyPrice;

    private String description;

    private Boolean isActive;
}
