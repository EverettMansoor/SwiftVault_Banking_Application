package com.example.demo.Entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "AllTransactions")
public class AllTransactions {

    @Id
    private ObjectId id;

    @DBRef
    private ObjectId accountId;

    @DBRef
    private List<ObjectId> transactions = new ArrayList<>();

    // Default constructor
    public AllTransactions() {
    }

    // Getter and Setter for id
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    // Getter and Setter for accountId
    public ObjectId getAccountId() {
        return accountId;
    }

    public void setAccountId(ObjectId accountId) {
        this.accountId = accountId;
    }

    // Getter and Setter for transactions
    public List<ObjectId> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<ObjectId> transactions) {
        this.transactions = transactions;
    }

    // Method to add a transaction to the list
    public void addTransaction(ObjectId transaction) {
        this.transactions.add(transaction);
    }

    // Method to remove a transaction from the list
    public void removeTransaction(ObjectId transaction) {
        this.transactions.remove(transaction);
    }
}
