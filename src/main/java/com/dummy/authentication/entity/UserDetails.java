package com.dummy.authentication.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "USER_DETAILS")
@Data
public class UserDetails {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userDetails")
    @PrimaryKeyJoinColumn
    private User user;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;
}
