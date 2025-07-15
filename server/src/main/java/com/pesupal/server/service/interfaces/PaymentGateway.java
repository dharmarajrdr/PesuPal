package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.PaymentDto;

public interface PaymentGateway {

    String generatePaymentLink(PaymentDto paymentDto) throws Exception;
}
