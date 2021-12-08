package com.rb.authentication.repository;

import com.rb.authentication.entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {
    List<CustomerDetails> findAll();

    CustomerDetails findByUsername(String username);
}
