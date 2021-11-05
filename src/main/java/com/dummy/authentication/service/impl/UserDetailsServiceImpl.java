package com.dummy.authentication.service.impl;

import com.dummy.authentication.entity.UserDetails;
import com.dummy.authentication.repository.UserDetailsRepository;
import com.dummy.authentication.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public List<UserDetails> getUserDetails() {
        return userDetailsRepository.findAll();
    }
}
