package com.rb.authentication.service.impl;

import com.rb.authentication.entity.CustomerDetails;
import com.rb.authentication.repository.CustomerDetailsRepository;
import com.rb.authentication.service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;

    public List<CustomerDetails> getCustomerDetails() {
        return customerDetailsRepository.findAll();
    }
}
