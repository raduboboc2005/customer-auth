package com.dummy.authentication.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "USER_DETAILS")
@Data
public class UserDetails {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =  "user_details_seq_generator")
    @SequenceGenerator(name = "user_details_seq_generator", sequenceName = "seq_user_details", allocationSize = 1)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @JsonBackReference
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
