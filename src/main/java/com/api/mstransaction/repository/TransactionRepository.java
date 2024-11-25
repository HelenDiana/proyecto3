package com.api.mstransaction.repository;

import com.api.mstransaction.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    Flux<Transaction> findBySourceAccountAndType(String sourceAccount, String type);
    Flux<Transaction> findByDestinationAccountAndType(String destinationAccount, String type);
}
