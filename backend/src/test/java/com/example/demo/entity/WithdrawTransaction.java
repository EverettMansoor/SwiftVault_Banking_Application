package com.example.demo.Entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "withdraw_transactions")
public class WithdrawTransaction extends Transaction {

    // Default constructor
    public WithdrawTransaction() {
        super();
    }

    @Override
    public void performTransaction(double amount) {
        if (getTransactionAmount() >= amount) {
            setTransactionAmount(getTransactionAmount() - amount);
        } else {
            throw new IllegalArgumentException("Insufficient balance for withdrawal.");
        }
    }

    // Getter and Setter for transactionId (inherited from Transaction class)
    @Override
    public ObjectId getTransactionId() {
        return super.getTransactionId();
    }

    @Override
    public void setTransactionId(ObjectId transactionId) {
        super.setTransactionId(transactionId);
    }

    // Getter and Setter for transactionType (inherited from Transaction class)
    @Override
    public String getTransactionType() {
        return super.getTransactionType();
    }

    @Override
    public void setTransactionType(String transactionType) {
        super.setTransactionType(transactionType);
    }

    // Getter and Setter for transactionAmount (inherited from Transaction class)
    @Override
    public Double getTransactionAmount() {
        return super.getTransactionAmount();
    }

    @Override
    public void setTransactionAmount(Double transactionAmount) {
        super.setTransactionAmount(transactionAmount);
    }

    // Getter and Setter for createdAt (inherited from Transaction class)
    @Override
    public Date getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        super.setCreatedAt(createdAt);
    }
}
