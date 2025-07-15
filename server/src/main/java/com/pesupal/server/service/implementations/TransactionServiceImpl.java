package com.pesupal.server.service.implementations;

import com.pesupal.server.model.payment.Transaction;
import com.pesupal.server.repository.TransactionRepository;
import com.pesupal.server.service.interfaces.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    /**
     * Creates a new transaction.
     *
     * @param transaction
     * @return
     */
    @Override
    public Transaction createTransaction(Transaction transaction) {

        return transactionRepository.save(transaction);
    }
}
