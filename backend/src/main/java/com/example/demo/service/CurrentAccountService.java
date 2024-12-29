package com.example.demo.service;

import com.example.demo.Entity.CurrentAccount;
import com.example.demo.Entity.ResponseEntities.SingleObjectResponse;
import com.example.demo.Entity.SavingAccount;
import com.example.demo.Entity.User;
import com.example.demo.repository.CurrentAccountRepo;
import com.example.demo.repository.SavingAccountRepo;
import com.example.demo.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrentAccountService {
    @Autowired
    private CurrentAccountRepo currentAccountRepo;

    @Autowired
    private UserRepo userRepo;

    // TO CREATE ACCOUNT
    public SingleObjectResponse<CurrentAccount> saveCurrentAccount(CurrentAccount currentAccount, ObjectId id) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return new SingleObjectResponse<>(null, "Account creation failed", false);
        }
        // Check if the user already has any type of account
        if (user.getAccount() != null) {
            return new SingleObjectResponse<>(null, "You already have an account. You cannot create another one.", false);
        }
        user.setAccount(currentAccount);
        userRepo.save(user);
        currentAccountRepo.save(currentAccount);
        return new SingleObjectResponse<>(currentAccount, "Account successfuly created", true);
    }

    // GET ALL THE SAVING ACCOUNTS
    public List<CurrentAccount> getAllSavingAccounts(){
        return currentAccountRepo.findAll();
    }

    // GET ACCOUNT BY ID
    public SingleObjectResponse<CurrentAccount> getAccountById(ObjectId id){
        CurrentAccount foundAcc = currentAccountRepo.findById(id).orElse(null);
        if(foundAcc == null){
            return new SingleObjectResponse<>(null, "Account not found", false);
        }
        return new SingleObjectResponse<>(foundAcc, "Account fetched Successfuly", true);
    }

    // DELETE ACCOUNT BY ID
    public boolean deleteAccountById(ObjectId id){
        currentAccountRepo.deleteById(id);
        return true;
    }
}
