package com.example.wear_shop.service;

import com.example.wear_shop.data.Entity.User;

import java.util.List;

public interface UserService {
    public List<User> searchUser(String username);
}
