package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.WebhookDto;

import java.util.List;

public interface WebhookService {

    WebhookDto createWebhook(String url, List<String> events);

    Boolean deleteWebhook(String webhookId);

    WebhookDto updateWebhook(String updatedUrl, List<String> events, String webhookId);
}
