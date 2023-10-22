package com.example.diploma.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String password;
    private String authority;

    public User(String userName, String password, String authority) {
        this.userName = userName;
        this.password = password;
        this.authority = authority;
    }
}
