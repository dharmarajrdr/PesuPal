package com.pesupal.server.factory;

import com.pesupal.server.enums.SupportedGateway;
import com.pesupal.server.service.interfaces.PaymentGateway;
import com.pesupal.server.strategies.payment_gateway.RazorpayGateway;
import com.pesupal.server.strategies.payment_gateway.StripeGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentGatewayFactory {

    private final RazorpayGateway razorpayGateway;

    private final StripeGateway stripeGateway;

    public PaymentGateway getPaymentGateway(SupportedGateway supportedGateway) {
        switch (supportedGateway) {
            case RAZORPAY: {
                return razorpayGateway;
            }
            case STRIPE: {
                return stripeGateway;
            }
            default: {
                throw new IllegalArgumentException("Unsupported supported gateway: " + supportedGateway);
            }
        }
    }
}
