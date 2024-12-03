package com.api.mstransaction.controller;

import com.api.mstransaction.model.Transaction;
import com.api.mstransaction.service.TransactionService;
import com.api.mstransaction.strategy.DepositStrategy;
import com.api.mstransaction.strategy.TransactionStrategy;
import com.api.mstransaction.strategy.WithdrawStrategy;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TransactionController {
    private final TransactionService transactionService;
    
    @PostMapping("/transactions/deposit")
    public ResponseEntity<String> desposit(@RequestBody Transaction transactionRequestBody) {
        TransactionStrategy strategy = new DepositStrategy();
        strategy.process(transactionRequestBody);
        transactionService.save(transactionRequestBody).subscribe();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/transactions/withdraw")
    public ResponseEntity<Map<String, Object>> withdraw(@RequestBody Transaction transactionRequestBody) {
        try {
            TransactionStrategy strategy = new WithdrawStrategy(transactionService);
            strategy.process(transactionRequestBody);
            transactionService.save(transactionRequestBody).subscribe();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }
    
    @PostMapping("/transactions/transfer")
    public ResponseEntity<Map<String, Object>> transfer(@RequestBody Transaction transactionRequestBody) {
        
        Double balance = transactionService.calculateBalance(transactionRequestBody.getSourceAccount());
        System.out.println("com.api.mstransaction.controller.TransactionController.transfer()" + balance);
        if (balance - transactionRequestBody.getAmount() < 0) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Saldo insuficiente para hacer transferencia");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }else{
             transactionRequestBody.setType("TRANSFERENCIA");
            transactionService.save(transactionRequestBody).subscribe();
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
    
    @GetMapping("/transactions/historial")
    public Observable<Transaction> listTransactions(@RequestHeader Map<String, String> headers) {
      return transactionService.listTransactions();
    }
    
}
