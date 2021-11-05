package com.dummy.authentication.service.impl;

import com.dummy.authentication.entity.User;
import com.dummy.authentication.repository.UserRepository;
import com.dummy.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
