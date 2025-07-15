package com.pesupal.server.enums;

public enum Interval {

    MONTHLY, YEARLY;

    public BillingFrequency toBillingFrequency() {
        return switch (this) {
            case MONTHLY -> BillingFrequency.MONTH;
            case YEARLY -> BillingFrequency.YEAR;
            default -> throw new IllegalArgumentException("Unknown interval: " + this);
        };
    }
}
