package com.example.wear_shop.config;

import com.example.wear_shop.data.Entity.User;
import com.example.wear_shop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.loadUserByUserName(username);
        if (users.size()>0)
        {
            return new UserCustom(users.get(0));
        }
        throw new UsernameNotFoundException("User details not found for the user : " + username);

    }
}
