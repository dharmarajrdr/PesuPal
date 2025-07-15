package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.response.WebhookDto;
import com.pesupal.server.service.interfaces.WebhookService;
import com.pesupal.server.strategies.payment_gateway.StripeGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebhookServiceImpl implements WebhookService {

    @Autowired
    private StripeGateway stripeGateway;

    public WebhookDto createWebhook(String url, List<String> events) {

        return stripeGateway.createWebhook(url, events);
    }

    public Boolean deleteWebhook(String webhookId) {

        return stripeGateway.deleteWebhook(webhookId);
    }

    public WebhookDto updateWebhook(String updatedUrl, List<String> events, String webhookId) {

        return stripeGateway.updateWebhook(updatedUrl, events, webhookId);
    }
}
