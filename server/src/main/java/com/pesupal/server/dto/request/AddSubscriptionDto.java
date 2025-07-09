package com.pesupal.server.dto.request;

import com.pesupal.server.enums.Currency;
import com.pesupal.server.enums.Interval;
import com.pesupal.server.model.subscription.SubscriptionPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSubscriptionDto {

    private String code;

    private Double price;

    private Currency currency;

    private Interval interval;

    private String description;

    private Long numberOfDays;

    public SubscriptionPlan toSubscriptionPlan() {

        SubscriptionPlan subscriptionPlan = new SubscriptionPlan();
        subscriptionPlan.setCode(this.code);
        subscriptionPlan.setPrice(this.price);
        subscriptionPlan.setCurrency(this.currency);
        subscriptionPlan.setInterval(this.interval);
        subscriptionPlan.setDescription(this.description);
        subscriptionPlan.setNumberOfDays(this.numberOfDays);
        return subscriptionPlan;
    }
}
