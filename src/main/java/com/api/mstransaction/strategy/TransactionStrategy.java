package com.api.mstransaction.strategy;

import com.api.mstransaction.model.Transaction;

public interface TransactionStrategy {
    void process(Transaction transaction);
}
