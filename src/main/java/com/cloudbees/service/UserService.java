package com.cloudbees.service;

import com.cloudbees.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User User);

    User findUserByUserName(String userName);

    List<User> findAllUsers();

    void deleteUserByUserName(String userName);
}
