package com.example.wear_shop.data.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String username;

    String password;

    String email;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;

    int status;

    String role;

    @Column(name = "created_at")
    Instant createAt;

    @Column(name = "updated_at")
    Instant updateAt;

    @Override
    public String toString() {
        return "{" +
                "username:'" + username + '\'' +
                ", email:'" + email + '\'' +
                ", role:'" + role + '\'' +
                '}';
    }
}
