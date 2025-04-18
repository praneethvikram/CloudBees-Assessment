package com.cloudbees.controller;


import com.cloudbees.dto.UserRequest;
import com.cloudbees.model.User;
import com.cloudbees.service.UserService;
import com.cloudbees.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserService userService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveUser(@RequestBody UserRequest userRequest) {
        userService.saveUser(MapperUtil.getUser(userRequest));
        return new ResponseEntity<>(HttpStatusCode.valueOf(201));
    }

    @GetMapping("/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String userName) {
        User user = userService.findUserByUserName(userName);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser() {
        return new ResponseEntity<List<User>>(userService.findAllUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<Void> deleteUserByUserName(@PathVariable String userName) {
        if (userService.findUserByUserName(userName) != null) {
            userService.deleteUserByUserName(userName);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
