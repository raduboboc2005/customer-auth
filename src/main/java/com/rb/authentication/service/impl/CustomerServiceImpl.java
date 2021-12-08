package com.rb.authentication.service.impl;

import com.rb.authentication.entity.Customer;
import com.rb.authentication.repository.CustomerRepository;
import com.rb.authentication.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
}
