package com.pesupal.server.model.subscription;

import com.pesupal.server.enums.Currency;
import com.pesupal.server.enums.Interval;
import com.pesupal.server.model.CreationTimeAuditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Entity
public class SubscriptionPlan extends CreationTimeAuditable {

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Interval interval;

    private String description;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private Long numberOfDays;

}
