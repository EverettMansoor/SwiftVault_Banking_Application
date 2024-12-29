package com.example.demo.controllers;

import com.example.demo.Entity.DepositTransaction;
import com.example.demo.Entity.ResponseEntities.SingleObjectResponse;
import com.example.demo.Entity.User;
import com.example.demo.Entity.WithdrawTransaction;
import com.example.demo.service.DepositTransactionService;
import com.example.demo.service.WithDrawTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction/deposit")
public class DepositTransactionController {
    @Autowired
    private DepositTransactionService depositTransactionService;

    @PostMapping("/perform_transaction/account/{id}")
    public ResponseEntity<SingleObjectResponse<DepositTransaction>> performDepositTransaction(
            @PathVariable String id, @RequestBody DepositTransaction depositTransaction) {
        if (id == null || id.isEmpty()) {
            SingleObjectResponse<DepositTransaction> resp = new SingleObjectResponse<>(null, "Failed to process: ID is null or empty", false);
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        SingleObjectResponse<DepositTransaction> resp = depositTransactionService.saveDepositTransaction(depositTransaction, new org.bson.types.ObjectId(id));
        if (!resp.isSuccess()) {
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/get_all_transactions")
    public Iterable<DepositTransaction> getAllDepositTransactions(){
        return depositTransactionService.getAllDepositTransactions();
    }

    @GetMapping("/get_transaction_by_id/{id}")
    public ResponseEntity<SingleObjectResponse<DepositTransaction>> getDepositTransactionById(@PathVariable String id) {
        if (id == null || id.isEmpty()) {
            SingleObjectResponse<DepositTransaction> resp = new SingleObjectResponse<>(null, "Failed to fetch: ID is null or empty", false);
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        SingleObjectResponse<DepositTransaction> resp = depositTransactionService.getDepositTransactionById(new org.bson.types.ObjectId(id));
        if (!resp.isSuccess()) {
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }


    @DeleteMapping("/delete_transaction_by_id/{id}")
    public boolean deleteDepositTransactionById(@PathVariable String id){
        return depositTransactionService.deleteDepositTransactionById(new org.bson.types.ObjectId(id));
    }
}
