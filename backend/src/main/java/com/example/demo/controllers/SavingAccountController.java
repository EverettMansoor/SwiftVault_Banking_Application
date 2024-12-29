package com.example.demo.controllers;

import com.example.demo.Entity.CurrentAccount;
import com.example.demo.Entity.ResponseEntities.SingleObjectResponse;
import com.example.demo.Entity.SavingAccount;
import com.example.demo.Entity.User;
import com.example.demo.service.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saving_account")
public class SavingAccountController {
    @Autowired
    private SavingAccountService savingAccountService;


    @GetMapping("/check")
    public String check(){
        return "Saving Account";
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<SingleObjectResponse<SavingAccount>> getAllAccounts(@PathVariable String id){
        if(id.isEmpty()){
            SingleObjectResponse<SavingAccount> resp = new SingleObjectResponse<>(null, "Id is not provided", false);
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }
        SingleObjectResponse<SavingAccount> resp = savingAccountService.getAccountById(new org.bson.types.ObjectId(id));
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/all_accounts")
    public List<SavingAccount> getAllAccounts(){
        return savingAccountService.getAllSavingAccounts();
    }

    @PostMapping("{userId}/register_account")
    public ResponseEntity<SingleObjectResponse<SavingAccount>> registerAccount(@PathVariable String userId, SavingAccount savingAccount){
        if(userId.isEmpty()){
            SingleObjectResponse<SavingAccount> resp = new SingleObjectResponse<>(null, "Account creation failed because id is null", false);
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }else{
            savingAccount.setAccountHolderId(new org.bson.types.ObjectId(userId));
            SingleObjectResponse<SavingAccount> resp = savingAccountService.saveSavingAccount(savingAccount, new org.bson.types.ObjectId(userId));
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete_account/{id}")
    public boolean deleteAccount(@PathVariable String id){
        return savingAccountService.deleteAccountById(new org.bson.types.ObjectId(id));
    }

}
