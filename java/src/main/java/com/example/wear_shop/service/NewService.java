package com.example.wear_shop.service;

import com.example.wear_shop.data.Entity.New;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface NewService {
    public Page<New> searchNew(String name, Pageable pageable);
}
