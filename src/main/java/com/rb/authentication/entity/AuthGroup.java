package com.rb.authentication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "AUTH_GROUP")
@Getter
@Setter
public class AuthGroup {

    @Id
    @Column(name = "GROUP_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer groupId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "AUTH_GROUP")
    private String authGroup;
}
