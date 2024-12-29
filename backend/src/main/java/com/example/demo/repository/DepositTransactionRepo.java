package com.example.demo.repository;

import com.example.demo.Entity.DepositTransaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositTransactionRepo extends MongoRepository<DepositTransaction, ObjectId> {
}
