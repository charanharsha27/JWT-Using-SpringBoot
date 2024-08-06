package com.jwt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String username;
    private String password;
    private String role;
}
