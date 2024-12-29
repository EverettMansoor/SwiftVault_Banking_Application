package com.example.demo.Entity;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "current_accounts")
public class CurrentAccount extends Account {

    private double overdraft = 10000;
    private String accountType = "Current";
    private List<Transaction> allTransactions;

    public CurrentAccount() {
        super();
    }

    // Getter and Setter for overdraft
    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
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
