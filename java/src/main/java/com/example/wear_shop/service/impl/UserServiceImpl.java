package com.example.wear_shop.service.impl;

import com.example.wear_shop.data.Entity.User;
import com.example.wear_shop.repo.UserRepository;
import com.example.wear_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> searchUser(String username) {
        return null;
    }
}
