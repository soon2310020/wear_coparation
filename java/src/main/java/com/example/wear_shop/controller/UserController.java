package com.example.wear_shop.controller;

import com.example.wear_shop.data.Entity.User;
import com.example.wear_shop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public void Create(@RequestBody User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUpdateAt(Instant.now());
        userRepository.save(user);

    }
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserInfor(@PathVariable(name = "username") String username)
    {
        List<User> user =userRepository.loadUserByUserName(username);
        Map<String,String> maps= new HashMap<String,String>();
        User userDetail = user.get(0);
        maps.put("username",userDetail.getUsername());
        maps.put("role",userDetail.getRole());
        maps.put("email",userDetail.getEmail());
        maps.put("id",userDetail.getId().toString());
        return ResponseEntity.ok(maps);
    }
}
