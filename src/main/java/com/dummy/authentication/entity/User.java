package com.dummy.authentication.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@Data
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =  "user_seq_generator")
    @SequenceGenerator(name = "user_seq_generator", sequenceName = "seq_users", allocationSize = 1)
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    @JsonManagedReference
    private UserDetails userDetails;
}
