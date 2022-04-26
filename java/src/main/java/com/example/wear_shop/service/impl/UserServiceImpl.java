package com.example.wear_shop.service.impl;

import com.example.wear_shop.data.Entity.User;
import com.example.wear_shop.repo.UserRepository;
import com.example.wear_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> searchUser(String username, Pageable pageable) {
        Page<User> users = userRepository.searchUser(username,pageable);
        return users;
    }
}
