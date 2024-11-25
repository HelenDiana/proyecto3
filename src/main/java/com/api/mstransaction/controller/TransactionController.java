package com.api.mstransaction.controller;

import com.api.mstransaction.model.Transaction;
import com.api.mstransaction.service.TransactionService;
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
        transactionRequestBody.setType("DEPOSITO");
        transactionRequestBody.setDestinationAccount("");
        System.out.println("com.api.mstransaction.controller.TransactionController.desposit():: "+ transactionRequestBody.toString());
        transactionService.save(transactionRequestBody).subscribe();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/transactions/withdraw")
    public ResponseEntity<Map<String, Object>> withdraw(@RequestBody Transaction transactionRequestBody) {
        Double balance = transactionService.calculateBalance(transactionRequestBody.getSourceAccount());
        if (balance - transactionRequestBody.getAmount() < 0) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Saldo insuficiente para retirar");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }else{
            transactionRequestBody.setType("RETIRO");
            transactionRequestBody.setDestinationAccount("");
            transactionService.save(transactionRequestBody).subscribe();
            return new ResponseEntity<>(HttpStatus.CREATED);
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
