package com.example.wear_shop.service;

import com.example.wear_shop.data.DTO.OrderDTO;
import com.example.wear_shop.data.DTO.ProductOrderReq;
import com.example.wear_shop.data.DTO.productDTO.ProductCreateReqDTO;
import com.example.wear_shop.data.DTO.productDTO.ProductReqDTO;
import com.example.wear_shop.data.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ProductService {
     Page<Product> getProduct(ProductReqDTO productReqDTO, Pageable pageable);
     Page<Product> getProductForfrontEnd(ProductReqDTO productReqDTO, Pageable pageable);
     void create(ProductCreateReqDTO createReqDTO);
     OrderDTO getOrderInfor(List<ProductOrderReq> reqs) throws IOException;
}
