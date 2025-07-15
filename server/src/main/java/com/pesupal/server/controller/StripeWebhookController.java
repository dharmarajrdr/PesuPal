package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreateWebhookDto;
import com.pesupal.server.dto.response.WebhookDto;
import com.pesupal.server.service.interfaces.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class StripeWebhookController {

    @Autowired
    private WebhookService webhookService;

    @PostMapping("/webhook")
    public WebhookDto createWebhook(@RequestBody CreateWebhookDto webhookDto) {

        return webhookService.createWebhook(webhookDto.getUrl(), webhookDto.getEvents());
    }

    @DeleteMapping("/webhook/{id}")
    public Boolean deleteWebhook(@PathVariable String id) {

        return webhookService.deleteWebhook(id);
    }

    @PatchMapping("/webhook/{id}")
    public WebhookDto deleteWebhook(@RequestBody CreateWebhookDto webhookDto, @PathVariable String id) {

        return webhookService.updateWebhook(webhookDto.getUrl(), webhookDto.getEvents(), id);
    }
}
