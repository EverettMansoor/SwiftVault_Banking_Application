package com.example.demo.repository;

import com.example.demo.Entity.SavingAccount;
import com.example.demo.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingAccountRepo extends MongoRepository<SavingAccount, ObjectId> {
}
