package com.rb.authentication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_DETAILS")
@Getter
@Setter
public class CustomerDetails {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customerDetails")
    @PrimaryKeyJoinColumn
    private Customer customer;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
}
