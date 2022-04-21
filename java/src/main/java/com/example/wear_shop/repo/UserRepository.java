package com.example.wear_shop.repo;

import com.example.wear_shop.data.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {
    @Query("select distinct u from User u where u.username = :name")
    List<User> loadUserByUserName(@Param("name") String username);


}
