package com.rb.authentication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER")
@Getter
@Setter
public class Customer {

    @Id
    @Column(name = "CUSTOMER_ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerDetails customerDetails;
}
