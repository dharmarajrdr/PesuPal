package com.pesupal.server.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pesupal.server.service.interfaces.PaymentService;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment-link")
    public String generatePaymentLink() throws Exception {

        return this.paymentService.initiatePayment();
    }
}
