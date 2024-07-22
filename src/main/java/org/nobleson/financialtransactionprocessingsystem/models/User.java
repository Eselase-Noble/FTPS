package org.nobleson.financialtransactionprocessingsystem.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String surname;
    private String otherNames;
    @Column(unique = true)
    private String email;
    private String password;



}
