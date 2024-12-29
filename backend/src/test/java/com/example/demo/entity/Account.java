package com.example.demo.Entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public abstract class Account {

    @Id
    private ObjectId id;

    private ObjectId accountHolderId;

    private double balance = 10000;

    // Default constructor
    public Account() {
    }

    // Getter and Setter for id
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    // Getter and Setter for accountHolderId
    public ObjectId getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(ObjectId accountHolderId) {
        this.accountHolderId = accountHolderId;
    }

    // Getter and Setter for balance
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
