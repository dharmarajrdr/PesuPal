package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreateWebhookDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.WebhookDto;
import com.pesupal.server.service.interfaces.WebhookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/stripe")
public class StripeWebhookController {

    private final WebhookService webhookService;

    @PostMapping("/webhook")
    public ResponseEntity<ApiResponseDto> createWebhook(@RequestBody CreateWebhookDto createWebhookDto) {

        WebhookDto webhookDto = webhookService.createWebhook(createWebhookDto.getUrl(), createWebhookDto.getEvents());
        return ResponseEntity.ok().body(new ApiResponseDto("Webhook created successfully", webhookDto));
    }

    @DeleteMapping("/webhook/{id}")
    public ResponseEntity<ApiResponseDto> deleteWebhook(@PathVariable String id) {

        Boolean webhookDeleted = webhookService.deleteWebhook(id);
        return ResponseEntity.ok().body(new ApiResponseDto("Webhook deleted successfully", webhookDeleted));
    }

    @PatchMapping("/webhook/{id}")
    public ResponseEntity<ApiResponseDto> updateWebhook(@RequestBody CreateWebhookDto updateWebhookDto, @PathVariable String id) {

        WebhookDto webhookDto = webhookService.updateWebhook(updateWebhookDto.getUrl(), updateWebhookDto.getEvents(), id);
        return ResponseEntity.ok().body(new ApiResponseDto("Webhook updated successfully", webhookDto));
    }
}
