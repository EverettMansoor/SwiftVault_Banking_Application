package com.example.demo.Entity;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "saving_accounts")
public class SavingAccount extends Account {

    private double interestRate = 6.0;
    private String accountType = "Saving";
    private List<Transaction> allTransactions;

    // Default constructor
    public SavingAccount() {
        super();  // Calls the constructor of the superclass Account
    }

    // Getter and Setter for interestRate
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    // Getter and Setter for accountType
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    // Getter and Setter for allTransactions
    public List<Transaction> getAllTransactions() {
        return allTransactions;
    }

    public void setAllTransactions(List<Transaction> allTransactions) {
        this.allTransactions = allTransactions;
    }
}
