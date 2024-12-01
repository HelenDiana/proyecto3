package com.api.mstransaction;

import com.api.mstransaction.model.Transaction;
import com.api.mstransaction.service.impl.TransactionServiceImpl;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class MstransactionApplicationTests {

    /*@Test
    void contextLoads() {
    }*/
    
    @Autowired
    private TransactionServiceImpl transactionServiceImpl;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);       
    }


    @Test
    public void testAllAccounts() {
        Observable<Transaction> transactions =  transactionServiceImpl.listTransactions();
        TestObserver<Transaction> testObserver = transactions.test();
        testObserver.assertNoErrors();
    }
    
    @Test
    public void testGuardarAccount() {
        Transaction _transaction = new Transaction();
        _transaction.setSourceAccount("1234567890");
        _transaction.setAmount(100.0);
        Mono<Transaction> result = transactionServiceImpl.save(_transaction);
        StepVerifier.create(result)
                .expectNext(_transaction)
                .expectComplete()
                .verify();
    }
}
