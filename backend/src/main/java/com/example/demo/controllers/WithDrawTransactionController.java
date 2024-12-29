package com.example.demo.controllers;

import com.example.demo.Entity.DepositTransaction;
import com.example.demo.Entity.ResponseEntities.SingleObjectResponse;
import com.example.demo.Entity.WithdrawTransaction;
import com.example.demo.service.WithDrawTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction/withdraw")
public class WithDrawTransactionController {
    @Autowired
    private WithDrawTransactionService withDrawTransactionService;

    @PostMapping("/perform_transaction/account/{Id}")
    public ResponseEntity<SingleObjectResponse<WithdrawTransaction>> performWithdrawTransaction(@PathVariable String Id, @RequestBody WithdrawTransaction withdrawTransaction){
        if(Id == null){
            SingleObjectResponse<WithdrawTransaction> resp = new SingleObjectResponse<>(null ,"Failed to fetch", false);
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        SingleObjectResponse<WithdrawTransaction> resp = withDrawTransactionService.saveWithdrawTransaction(withdrawTransaction, new org.bson.types.ObjectId(Id));
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/get_all_transactions")
    public Iterable<WithdrawTransaction> getAllWithdrawTransactions(){
        return withDrawTransactionService.getAllWithdrawTransactions();
    }

    @GetMapping("/get_transaction_by_id/{id}")
    public ResponseEntity<SingleObjectResponse<WithdrawTransaction>> getWithDrawTransactionById(@PathVariable String id) {
        if (id == null || id.isEmpty()) {
            SingleObjectResponse<WithdrawTransaction> resp = new SingleObjectResponse<>(null, "Failed to fetch: ID is null or empty", false);
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        SingleObjectResponse<WithdrawTransaction> resp = withDrawTransactionService.getWithDrawTransactionById(new org.bson.types.ObjectId(id));
        if (!resp.isSuccess()) {
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/delete_transaction_by_id/{id}")
    public boolean deleteWithDrawTransactionById(@PathVariable String id){
        return withDrawTransactionService.deleteWithDrawTransactionById(new org.bson.types.ObjectId(id));
    }
}
