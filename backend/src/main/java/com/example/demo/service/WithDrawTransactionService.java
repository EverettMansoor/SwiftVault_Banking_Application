package com.example.demo.service;

import com.example.demo.Entity.*;
import com.example.demo.Entity.ResponseEntities.SingleObjectResponse;
import com.example.demo.repository.AllTransactionsRepo;
import com.example.demo.repository.CurrentAccountRepo;
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
public class WithDrawTransactionService {
    @Autowired
    private WithDrawTransactionRepo withDrawTransactionRepo;

    @Autowired
    private CurrentAccountRepo currentAccountRepo;

    @Autowired
    private SavingAccountRepo savingAccountRepo;

    @Autowired
    private AllTransactionsRepo allTransactionsRepo;


    //    Perform and Save Transaction
    public SingleObjectResponse<WithdrawTransaction> saveWithdrawTransaction(WithdrawTransaction withdrawTransaction, ObjectId id) {
        if (id == null) {
            return new SingleObjectResponse<>(null, "Account ID is null", false);
        }

        CurrentAccount currentAccount = currentAccountRepo.findById(id).orElse(null);
        SavingAccount savingAccount = savingAccountRepo.findById(id).orElse(null);

        if (currentAccount == null && savingAccount == null) {
            return new SingleObjectResponse<>(null, "Account ID not found", false);
        }

        if (currentAccount != null) {
            double totalAvailableFunds = currentAccount.getBalance() + currentAccount.getOverdraft();
            double withdrawalAmount = withdrawTransaction.getTransactionAmount();

            if (totalAvailableFunds < withdrawalAmount) {
                return new SingleObjectResponse<>(null, "Insufficient funds", false);
            }

            if (currentAccount.getBalance() >= withdrawalAmount) {
                currentAccount.setBalance(currentAccount.getBalance() - withdrawalAmount);
            } else {
                double remainingAmount = withdrawalAmount - currentAccount.getBalance();
                currentAccount.setBalance(0);
                currentAccount.setOverdraft(currentAccount.getOverdraft() - remainingAmount);
            }

            if (currentAccount.getAllTransactions() == null) {
                currentAccount.setAllTransactions(new ArrayList<>());
            }
            currentAccount.getAllTransactions().add(withdrawTransaction);
            currentAccountRepo.save(currentAccount);
        }

        if (savingAccount != null) {
            if (savingAccount.getBalance() < withdrawTransaction.getTransactionAmount()) {
                return new SingleObjectResponse<>(null, "Insufficient funds", false);
            }
            savingAccount.setBalance(savingAccount.getBalance() - withdrawTransaction.getTransactionAmount());

            if (savingAccount.getAllTransactions() == null) {
                savingAccount.setAllTransactions(new ArrayList<>());
            }
            savingAccount.getAllTransactions().add(withdrawTransaction);
            savingAccountRepo.save(savingAccount);
        }
        withdrawTransaction.setCreatedAt(convertToDate(LocalDateTime.now()));
        WithdrawTransaction resp = withDrawTransactionRepo.save(withdrawTransaction);
        return new SingleObjectResponse<>(resp, "Transaction successfully completed", true);
    }


//    Getting All Transactions
    public List<WithdrawTransaction> getAllWithdrawTransactions(){
        return withDrawTransactionRepo.findAll();
    }

    private Date convertToDate(LocalDateTime now) {
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }


    //    BY ID
    public SingleObjectResponse<WithdrawTransaction> getWithDrawTransactionById(ObjectId id) {
        if (id == null) {
            return new SingleObjectResponse<>(null, "ID is null", false);
        }

        WithdrawTransaction transaction = withDrawTransactionRepo.findById(id).orElse(null);
        if (transaction == null) {
            return new SingleObjectResponse<>(null, "Transaction not found", false);
        }

        return new SingleObjectResponse<>(transaction, "Transaction fetched successfully", true);
    }


    public boolean deleteWithDrawTransactionById(ObjectId id){
        withDrawTransactionRepo.deleteById(id);
        return true;
    }
}
