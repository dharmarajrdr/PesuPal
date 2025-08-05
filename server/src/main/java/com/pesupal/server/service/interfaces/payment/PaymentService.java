package com.pesupal.server.service.interfaces.payment;

import com.pesupal.server.dto.request.PaymentDto;

public interface PaymentService {

    String initiatePayment(PaymentDto paymentDto) throws Exception;
}
