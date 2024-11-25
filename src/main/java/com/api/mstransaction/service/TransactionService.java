package com.api.mstransaction.service;

import com.api.mstransaction.model.Transaction;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    public Observable<Transaction> listTransactions();
    public Mono<Transaction> save(Transaction transaction);
    public Double calculateBalance(String sourceAccount);
}
