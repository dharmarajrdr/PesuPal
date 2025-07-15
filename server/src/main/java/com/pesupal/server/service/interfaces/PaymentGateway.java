package com.pesupal.server.service.interfaces;

public interface PaymentGateway {

    String generatePaymentLink() throws Exception;
}
