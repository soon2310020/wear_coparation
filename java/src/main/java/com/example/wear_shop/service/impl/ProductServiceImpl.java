package com.example.wear_shop.service.impl;

import com.example.wear_shop.data.DTO.productDTO.ProductReqDTO;
import com.example.wear_shop.data.Entity.Product;
import com.example.wear_shop.repo.ProductRepository;
import com.example.wear_shop.service.ProductService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> getProduct(ProductReqDTO productReqDTO) {
        return null;
    }
}
