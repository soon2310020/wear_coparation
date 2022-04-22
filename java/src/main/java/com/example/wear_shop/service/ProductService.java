package com.example.wear_shop.service;

import com.example.wear_shop.data.DTO.productDTO.ProductCreateReqDTO;
import com.example.wear_shop.data.DTO.productDTO.ProductReqDTO;
import com.example.wear_shop.data.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
     Page<Product> getProduct(ProductReqDTO productReqDTO, Pageable pageable);
     void create(ProductCreateReqDTO createReqDTO);
}
