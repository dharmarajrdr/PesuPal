package com.pesupal.server.strategies.payment_gateway;

import com.pesupal.server.service.interfaces.PaymentGateway;
import org.springframework.stereotype.Service;

@Service
public class RazorpayGateway implements PaymentGateway {

    @Override
    public String generatePaymentLink() {

        throw new UnsupportedOperationException("Razorpay payment link generation is not implemented yet.");
    }
}
