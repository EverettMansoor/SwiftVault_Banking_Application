package com.example.demo.service;

import com.example.demo.Entity.CurrentAccount;
import com.example.demo.Entity.ResponseEntities.SingleObjectResponse;
import com.example.demo.Entity.SavingAccount;
import com.example.demo.Entity.User;
import com.example.demo.repository.SavingAccountRepo;
import com.example.demo.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SavingAccountService {
    @Autowired
    private SavingAccountRepo savingAccountRepo;

    @Autowired
    private UserRepo userRepo;

    // Saving Account
    public SingleObjectResponse<SavingAccount> saveSavingAccount(SavingAccount savingAccount, ObjectId id) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return new SingleObjectResponse<>(null, "Account creation failed because no user found", false);
        }
        // Check if the user already has any type of account
        if (user.getAccount() != null) {
            return new SingleObjectResponse<>(null, "You already have an account. You cannot create another one.", false);
        }
        user.setAccount(savingAccount);
        userRepo.save(user);
        savingAccountRepo.save(savingAccount);
        return new SingleObjectResponse<>(savingAccount, "Account created successfuly", true);
    }

    // GET ALL SAVING ACCOUNTS
    public List<SavingAccount> getAllSavingAccounts(){
        return savingAccountRepo.findAll();
    }

    // GET ACCOUNT BY ID
    public SingleObjectResponse<SavingAccount> getAccountById(ObjectId id){
        SavingAccount foundAcc = savingAccountRepo.findById(id).orElse(null);
        if(foundAcc == null){
            return new SingleObjectResponse<>(null, "Account not found", false);
        }
        return new SingleObjectResponse<>(foundAcc, "Account fetched Successfuly", true);
    }

    // DELETE BY ID
    public boolean deleteAccountById(ObjectId id){
        savingAccountRepo.deleteById(id);
        return true;
    }
}
