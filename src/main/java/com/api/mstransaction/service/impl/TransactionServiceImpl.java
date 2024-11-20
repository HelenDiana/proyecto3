package com.api.mstransaction.service.impl;

import com.api.mstransaction.model.Transaction;
import com.api.mstransaction.repository.TransactionRepository;
import com.api.mstransaction.service.TransactionService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    
    @Override
    public Mono<Transaction> save(Transaction transaction) {        
        Transaction _transaction = new Transaction();
        _transaction.setType(transaction.getType());
        _transaction.setAmount(transaction.getAmount());
        _transaction.setSourceAccount(transaction.getSourceAccount());
        _transaction.setDestinationAccount(transaction.getDestinationAccount());
        return transactionRepository.save(transaction);        
    }
    
    @Override
    public Observable<Transaction> listTransactions() {
        return Observable.fromPublisher(transactionRepository.findAll());
    }
}
