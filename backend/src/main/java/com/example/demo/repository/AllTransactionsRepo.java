package com.example.demo.repository;

import com.example.demo.Entity.AllTransactions;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllTransactionsRepo extends MongoRepository<AllTransactions, ObjectId> {
}
