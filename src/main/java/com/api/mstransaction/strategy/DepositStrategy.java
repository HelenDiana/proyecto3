package com.api.mstransaction.strategy;

import com.api.mstransaction.model.Transaction;

public class DepositStrategy implements TransactionStrategy {
    @Override
    public void process(Transaction transaction) {
        // Lógica específica para el depósito
        transaction.setType("DEPOSITO");
        transaction.setDestinationAccount("");  // Lógica de destino vacía para depósitos
    }
}