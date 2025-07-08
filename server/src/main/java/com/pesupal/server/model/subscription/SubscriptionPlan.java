package com.pesupal.server.model.subscription;

import com.pesupal.server.enums.Currency;
import com.pesupal.server.enums.Interval;
import com.pesupal.server.model.CreationTimeAuditable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Entity
public class SubscriptionPlan extends CreationTimeAuditable {

    private String code;

    private Double price;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private Interval interval;

    private String description;

    private Boolean active;

    private Boolean includesTrial;
}
