package com.cloudbees.service;

import com.cloudbees.model.User;
import com.cloudbees.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public UserRepository UserRepository;

    @Override
    public void saveUser(User User) {
        UserRepository.save(User);
    }

    @Override
    public User findUserByUserName(String userName) {
        Optional<User> user = UserRepository.findById(userName);
        return user.orElse(null);
    }

    @Override
    public List<User> findAllUsers() {
        return UserRepository.findAll();
    }

    @Override
    public void deleteUserByUserName(String userName) {
        User User = findUserByUserName(userName);
        if (User == null) {
            return;
        }
        UserRepository.deleteById(User.getUserName());
    }
}
