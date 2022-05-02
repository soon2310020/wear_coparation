package com.example.wear_shop.controller;

import com.example.wear_shop.data.DTO.OrderReq;
import com.example.wear_shop.data.DTO.OrderSearchReq;
import com.example.wear_shop.data.Entity.Order;
import com.example.wear_shop.repo.OrderRepository;
import com.example.wear_shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @PostMapping("/add_order")
    public void create(@RequestBody OrderReq req)
    {
      orderService.createOrder(req);
    }
    @PostMapping("/search")
    public ResponseEntity<Page<Order>> getOrder(@RequestBody OrderSearchReq req, Pageable pageable)
    {
        return ResponseEntity.ok(orderService.findOrder(req,pageable));
    }
    @PutMapping("/status")
    public void changeStatus(@RequestParam Long id,@RequestParam Long status)
    {
        Order order =orderRepository.getById(id);
        order.setUpdatedAt(Instant.now());
        order.setStatus(status);
        orderRepository.save(order);
    }
    @GetMapping("/chart")

    public ResponseEntity<List<Long>> getChartOrder()
    {
      return ResponseEntity.ok(orderService.getChart());
    }

    @GetMapping("/chart_all")
    public ResponseEntity<Long> getAllOrder()
    {
        return ResponseEntity.ok(orderRepository.countAllOrder());
    }


}
