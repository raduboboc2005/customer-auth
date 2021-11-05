package com.dummy.authentication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class User {

    @Id
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "USER_ID")
    private UserDetails userDetails;
}
