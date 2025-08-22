package com.pesupal.server.enums;

public enum WebhookStatus {

    ENABLED,
    DISABLED;

    public static WebhookStatus fromString(String status) {

        for (WebhookStatus webhookStatus : WebhookStatus.values()) {
            if (webhookStatus.name().equalsIgnoreCase(status)) {
                return webhookStatus;
            }
        }
        throw new IllegalArgumentException("Unknown webhook status: " + status);
    }
}
