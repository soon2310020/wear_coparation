package com.example.wear_shop.controller;

import com.example.wear_shop.data.Entity.User;
import com.example.wear_shop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public void Create(@RequestBody User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUpdateAt(Instant.now());
        userRepository.save(user);

    }
}
