package com.example.demo.service;

import com.example.demo.Entity.LoginRequest;
import com.example.demo.Entity.ResponseEntities.MultipleObjectsResponse;
import com.example.demo.Entity.ResponseEntities.SingleObjectResponse;
import com.example.demo.Entity.User;
import com.example.demo.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public SingleObjectResponse<User> saveEntity(User user){
        user.setUserName(user.getFirstName().toLowerCase()+"_sw_123");
        try {
            User res = userRepo.save(user);
            return new SingleObjectResponse<>(res, "User registered successfully", true);
        } catch (Exception e) {
            return new SingleObjectResponse<>(null, "User registration failed: " + e.getMessage(), false);
        }
    }

    public MultipleObjectsResponse<User> getAll() {
        List<User> users = userRepo.findAll();
        if(users.isEmpty()){
            return new MultipleObjectsResponse<User>(null,"There is no users found",false);
        }
        return new MultipleObjectsResponse<User>(users, "Successfuly fetched users", true);
    }


    public SingleObjectResponse<User> getById(ObjectId id){
        User user = userRepo.findById(id).orElse(null);
        if(user == null){
            return new SingleObjectResponse<User>(null, "No user found", false);
        }
        return new SingleObjectResponse<User>(user, "User fetched successfuly", true);
    }

    public boolean deleteById(ObjectId id) {
        userRepo.deleteById(id);
        return true;
    }

    public User findUserByEmail(String email) {
        return userRepo.findByEmail(String.valueOf(email));
    }

    // Login

    public SingleObjectResponse<User> checkUserCredentials(LoginRequest loginRequest){
        User isExist = findUserByEmail(loginRequest.getEmail());
        if(isExist != null){
            // check password
            if(loginRequest.getPassword().equals(isExist.getPassword())){
                return new SingleObjectResponse<User>(isExist, "User successfuly logged in", true);
            }else{
                return  new SingleObjectResponse<User>(null, "Wrong password", false);
            }
        }
        else{
            return  new SingleObjectResponse<User>(null, "This email is not belongs to any account", false);
        }
    }

}
