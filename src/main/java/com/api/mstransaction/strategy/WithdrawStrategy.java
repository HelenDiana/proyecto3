package com.api.mstransaction.strategy;

import com.api.mstransaction.model.Transaction;
import com.api.mstransaction.service.TransactionService;

public class WithdrawStrategy implements TransactionStrategy {
    private final TransactionService transactionService;

    public WithdrawStrategy(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void process(Transaction transaction) {
        Double balance = transactionService.calculateBalance(transaction.getSourceAccount());
        if (balance - transaction.getAmount() < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para retirar");
        }
        transaction.setType("RETIRO");
        transaction.setDestinationAccount("");  // Lógica de destino vacía para retiros
    }
}
