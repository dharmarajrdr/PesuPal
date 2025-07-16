package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.PaymentDto;
import com.pesupal.server.enums.SupportedGateway;
import com.pesupal.server.factory.PaymentGatewayFactory;
import com.pesupal.server.service.interfaces.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentGatewayFactory paymentGatewayFactory;

    @Override
    public String initiatePayment(PaymentDto paymentDto) throws Exception {

        SupportedGateway gateway = SupportedGateway.STRIPE; // Dynamic
        return paymentGatewayFactory.getPaymentGateway(gateway).generatePaymentLink(paymentDto);
    }
}
