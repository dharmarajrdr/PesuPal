package com.pesupal.server.controller;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.service.interfaces.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment-link")
    public ResponseEntity<ApiResponseDto> generatePaymentLink() throws Exception {

        String paymentLink = this.paymentService.initiatePayment();
        return ResponseEntity.ok().body(new ApiResponseDto("Payment link generated successfully", paymentLink));
    }
}
