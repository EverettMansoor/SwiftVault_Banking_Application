package com.example.demo.service;

import com.example.demo.Entity.AllTransactions;
import com.example.demo.Entity.SavingAccount;
import com.example.demo.repository.AllTransactionsRepo;
import com.example.demo.repository.SavingAccountRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllTransactionsService {
    @Autowired
    private AllTransactionsRepo allTransactionsRepo;

//    public AllTransactions saveAtransaction(ObjectId , ObjectId id) {
//        allTransactions.setAccountId(id);
//        return allTransactionsRepo.save(allTransactions)
//    }

//    public List<SavingAccount> getAllSavingAccounts(){
//        return savingAccountRepo.findAll();
//    }
//
//    public SavingAccount getAccountById(ObjectId id){
//        return savingAccountRepo.findById(id).orElse(null);
//    }
//
//    public boolean deleteAccountById(ObjectId id){
//        savingAccountRepo.deleteById(id);
//        return true;
//    }
}
