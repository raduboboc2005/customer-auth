package com.dummy.authentication.service;

import com.dummy.authentication.entity.UserDetails;

import java.util.List;

public interface UserDetailsService {
    List<UserDetails> getUserDetails();
}
