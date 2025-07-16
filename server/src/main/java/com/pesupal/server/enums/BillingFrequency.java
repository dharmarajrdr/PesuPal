package com.pesupal.server.enums;

import com.stripe.param.PlanCreateParams;

public enum BillingFrequency {
    DAY,
    MONTH,
    WEEK,
    YEAR;

    public static PlanCreateParams.Interval toInterval(BillingFrequency billingFrequency) {
        switch (billingFrequency) {
            case DAY:
                return PlanCreateParams.Interval.DAY;
            case WEEK:
                return PlanCreateParams.Interval.WEEK;
            case MONTH:
                return PlanCreateParams.Interval.MONTH;
            case YEAR:
                return PlanCreateParams.Interval.YEAR;
            default:
                throw new IllegalArgumentException("Unknown interval: " + billingFrequency);
        }
    }
}
