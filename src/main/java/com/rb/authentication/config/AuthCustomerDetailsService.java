package com.rb.authentication.config;

import com.rb.authentication.entity.AuthGroup;
import com.rb.authentication.entity.Customer;
import com.rb.authentication.repository.AuthGroupRepository;
import com.rb.authentication.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthCustomerDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final CustomerRepository customerRepository;
    private final AuthGroupRepository authGroupRepository;

    public AuthCustomerDetailsService(CustomerRepository customerRepository, AuthGroupRepository authGroupRepository) {
        super();
        this.customerRepository = customerRepository;
        this.authGroupRepository = authGroupRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = this.customerRepository.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("cannot find username: " + username);
        }

        List<AuthGroup> authGroups = this.authGroupRepository.findByUsername(username);
        return new AuthCustomerPrincipal(customer, authGroups);
    }
}