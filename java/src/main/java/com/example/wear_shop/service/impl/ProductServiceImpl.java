package com.example.wear_shop.service.impl;

import com.example.wear_shop.repo.ProductRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl {
    @Autowired
    private ProductRepository productRepository;

    
}
