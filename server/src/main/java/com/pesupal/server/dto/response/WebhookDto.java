package com.pesupal.server.dto.response;

import com.pesupal.server.enums.WebhookStatus;
import com.stripe.model.WebhookEndpoint;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class WebhookDto {

    private String id;

    private String secret;

    private List<String> events = new ArrayList<>();

    private String url;

    private WebhookStatus status;

    public static WebhookDto fromWebhookEndpoint(WebhookEndpoint webhookEndpoint) {

        WebhookDto webhook = new WebhookDto();
        webhook.setId(webhookEndpoint.getId());
        webhook.setSecret(webhookEndpoint.getSecret());
        webhook.setEvents(webhookEndpoint.getEnabledEvents());
        webhook.setUrl(webhookEndpoint.getUrl());
        webhook.setStatus(WebhookStatus.fromString(webhookEndpoint.getStatus()));
        return webhook;
    }
}
