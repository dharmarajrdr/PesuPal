package com.pesupal.server.service.interfaces;

import com.pesupal.server.model.payment.Transaction;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);
}
