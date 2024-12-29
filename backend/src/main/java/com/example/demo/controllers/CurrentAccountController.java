package com.example.demo.controllers;

import com.example.demo.Entity.CurrentAccount;
import com.example.demo.Entity.ResponseEntities.SingleObjectResponse;
import com.example.demo.Entity.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.CurrentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/current_account")
public class CurrentAccountController {
    @Autowired
    private CurrentAccountService currentAccountService;

    @GetMapping("/account/{id}")
    public ResponseEntity<SingleObjectResponse<CurrentAccount>> getAllAccounts(@PathVariable String id){
        if(id.isEmpty()){
            SingleObjectResponse<CurrentAccount> resp = new SingleObjectResponse<>(null, "Id is not provided", false);
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }
        SingleObjectResponse<CurrentAccount> resp = currentAccountService.getAccountById(new org.bson.types.ObjectId(id));
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/all_accounts")
    public List<CurrentAccount> getAllAccounts(){
        return currentAccountService.getAllSavingAccounts();
    }

    @PostMapping("{userId}/register_account")
    public ResponseEntity<SingleObjectResponse<CurrentAccount>> registerAccount(@PathVariable String userId, CurrentAccount currentAccount){
        if(userId.isEmpty()){
            SingleObjectResponse<CurrentAccount> resp = new SingleObjectResponse<CurrentAccount>(null, "Account creation failed because ID is null", false);
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }else{
            currentAccount.setAccountHolderId(new org.bson.types.ObjectId(userId));
            SingleObjectResponse<CurrentAccount> resp = currentAccountService.saveCurrentAccount(currentAccount, new org.bson.types.ObjectId(userId));
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete_account/{id}")
    public boolean deleteAccount(@PathVariable String id){
        return currentAccountService.deleteAccountById(new org.bson.types.ObjectId(id));
    }

}
