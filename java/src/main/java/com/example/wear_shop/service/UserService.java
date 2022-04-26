package com.example.wear_shop.service;

import com.example.wear_shop.data.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    public Page<User> searchUser(String username, Pageable pageable);
}
