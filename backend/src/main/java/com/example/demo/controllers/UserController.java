package com.example.demo.controllers;

import com.example.demo.Entity.CurrentAccount;
import com.example.demo.Entity.LoginRequest;
import com.example.demo.Entity.ResponseEntities.MultipleObjectsResponse;
import com.example.demo.Entity.ResponseEntities.SingleObjectResponse;
import com.example.demo.Entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        MultipleObjectsResponse<User> res =  userService.getAll();
        if(res.isSuccess()){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/register")
    public ResponseEntity<SingleObjectResponse<User>> createUser(@RequestBody User user) {
        SingleObjectResponse<User> res = userService.saveEntity(user);
        if (res.isSuccess()) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        SingleObjectResponse<User> res = userService.checkUserCredentials(loginRequest);
        if(res.isSuccess()){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

//    @PutMapping("/{userName}")
//    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String userName) {
//        User userInDB = userService.findUserByName(userName);
//        if (userInDB != null) {
//            userInDB.setUserName(user.getUserName());
//            userInDB.setPassword(user.getPassword());
//            User updatedUser = userService.saveEntity(userInDB);
//            return new ResponseEntity<>(updatedUser, HttpStatus.OK); // Returning updated user
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<SingleObjectResponse<User>> getUserById(@PathVariable String id) {
        if (id == null){
            SingleObjectResponse<User> resp = new SingleObjectResponse<User>(null, "Account creation failed because ID is null", false);
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        SingleObjectResponse<User> resp = userService.getById(new org.bson.types.ObjectId(id));
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
