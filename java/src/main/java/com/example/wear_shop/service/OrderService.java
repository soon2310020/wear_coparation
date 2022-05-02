package com.example.wear_shop.service;

import com.example.wear_shop.data.DTO.OrderReq;
import com.example.wear_shop.data.DTO.OrderSearchReq;
import com.example.wear_shop.data.Entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    public void createOrder(OrderReq orderReq);
    public Page<Order> findOrder(OrderSearchReq req, Pageable pageable);
    public List<Long> getChart();
}
