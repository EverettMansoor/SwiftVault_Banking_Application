package com.example.demo.service;

import com.example.demo.Entity.*;
import com.example.demo.Entity.ResponseEntities.SingleObjectResponse;
import com.example.demo.repository.CurrentAccountRepo;
import com.example.demo.repository.DepositTransactionRepo;
import com.example.demo.repository.SavingAccountRepo;
import com.example.demo.repository.WithDrawTransactionRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DepositTransactionService {

    @Autowired
    private DepositTransactionRepo depositTransactionRepo;

    @Autowired
    private CurrentAccountRepo currentAccountRepo;

    @Autowired
    private SavingAccountRepo savingAccountRepo;


    // PERFORM DEPOSIT TRANSACTION
    public SingleObjectResponse<DepositTransaction> saveDepositTransaction(DepositTransaction depositTransaction, ObjectId id) {
        if (id == null) {
            return new SingleObjectResponse<>(null, "Account ID is null", false);
        }

        CurrentAccount currentAccount = currentAccountRepo.findById(id).orElse(null);
        SavingAccount savingAccount = savingAccountRepo.findById(id).orElse(null);

        if (currentAccount == null && savingAccount == null) {
            return new SingleObjectResponse<>(null, "Account ID not found", false);
        }

        if (currentAccount != null) {
            if (currentAccount.getAllTransactions() == null) {
                currentAccount.setAllTransactions(new ArrayList<>());
            }

            double depositAmount = depositTransaction.getTransactionAmount();

            // Adjust overdraft if we ...
            if (currentAccount.getOverdraft() < 10000) {
                double overdraftDifference = 10000 - currentAccount.getOverdraft();
                if (depositAmount >= overdraftDifference) {
                    currentAccount.setOverdraft(10000);
                    depositAmount -= overdraftDifference;
                } else {
                    currentAccount.setOverdraft(currentAccount.getOverdraft() + depositAmount);
                    depositAmount = 0;
                }
            }

            currentAccount.setBalance(currentAccount.getBalance() + depositAmount);

            currentAccount.getAllTransactions().add(depositTransaction);

            currentAccountRepo.save(currentAccount);
        }

        if (savingAccount != null) {
            if (savingAccount.getAllTransactions() == null) {
                savingAccount.setAllTransactions(new ArrayList<>());
            }
            savingAccount.setBalance(savingAccount.getBalance() + depositTransaction.getTransactionAmount());
            savingAccount.getAllTransactions().add(depositTransaction);
            savingAccountRepo.save(savingAccount);
        }
        depositTransaction.setCreatedAt(convertToDate(LocalDateTime.now()));
        DepositTransaction resp = depositTransactionRepo.save(depositTransaction);
        return new SingleObjectResponse<>(resp, "Successfully completed the transaction", true);
    }

    // DATE FUNCTION
    private Date convertToDate(LocalDateTime now) {
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    // GET ALL DEPOSITS
    public List<DepositTransaction> getAllDepositTransactions(){
        return depositTransactionRepo.findAll();
    }

    // GET DEPOSIT BY ID
    public SingleObjectResponse<DepositTransaction> getDepositTransactionById(ObjectId id) {
        if (id == null) {
            return new SingleObjectResponse<>(null, "ID is null", false);
        }

        DepositTransaction transaction = depositTransactionRepo.findById(id).orElse(null);
        if (transaction == null) {
            return new SingleObjectResponse<>(null, "Transaction not found", false);
        }

        return new SingleObjectResponse<>(transaction, "Transaction fetched successfully", true);
    }

    // Method to delete deposit transaction by ID
    public boolean deleteDepositTransactionById(ObjectId id){
        depositTransactionRepo.deleteById(id);
        return true;
    }
}
