package com.example.demo.Entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "transactions")
public abstract class Transaction {

    @Id
    private ObjectId transactionId;

    private String transactionType;
    private Double transactionAmount;
    private Date createdAt;

    // Default constructor
    public Transaction() {
        this.createdAt = new Date();  // Set createdAt to current date if no value is provided
    }

    // Constructor with parameters
    public Transaction(String transactionType, Double transactionAmount) {
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.createdAt = new Date();  // Set createdAt to current date
    }

    // Getter and Setter for transactionId
    public ObjectId getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(ObjectId transactionId) {
        this.transactionId = transactionId;
    }

    // Getter and Setter for transactionType
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    // Getter and Setter for transactionAmount
    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    // Getter and Setter for createdAt
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Abstract method to perform a transaction
    public abstract void performTransaction(double amount);
}
