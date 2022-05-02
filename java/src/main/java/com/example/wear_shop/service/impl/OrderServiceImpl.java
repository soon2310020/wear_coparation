package com.example.wear_shop.service.impl;

import com.example.wear_shop.data.DTO.OrderReq;
import com.example.wear_shop.data.DTO.OrderSearchReq;
import com.example.wear_shop.data.Entity.Order;
import com.example.wear_shop.data.Entity.OrderDetails;
import com.example.wear_shop.data.Entity.Product;
import com.example.wear_shop.data.Entity.User;
import com.example.wear_shop.repo.OrderRepository;
import com.example.wear_shop.repo.ProductRepository;
import com.example.wear_shop.repo.UserRepository;
import com.example.wear_shop.service.MailService;
import com.example.wear_shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MailService mailService;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createOrder(OrderReq orderReq) {
        Order order = new Order();
        order.setAddress(orderReq.getAddress());
        order.setMobile(orderReq.getPhoneNumber());
        User user =userRepository.getById(orderReq.getUserId());
        order.setUser(user);
        order.setReceivedName(orderReq.getReceivedName());
        order.setStatus(1L);
        order.setUpdatedAt(Instant.now());
        List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
        AtomicReference<Long> totalPrice = new AtomicReference<>(0L);
     orderReq.getProducts().forEach(p ->
     {

         OrderDetails orderDetails = new OrderDetails();
         orderDetails.setQuatity(p.getQuality());
         Product product = productRepository.getById(p.getId());
         totalPrice.updateAndGet(v -> v + product.getPrice() - product.getPrice() * (product.getDiscount() / 100));
         orderDetails.setProduct(product);
         orderDetails.setSize(p.getSize());
         orderDetails.setOrder(order);
         orderDetailsList.add(orderDetails);
     });
     if (totalPrice.get() < 1000000)
     {
         totalPrice.updateAndGet(v -> v + 30000);
     }
     order.setOrderDetails(orderDetailsList);
     order.setPriceTotal(totalPrice.get());
     Order orderSave=orderRepository.save(order);
     mailService.sendMail(user.getEmail(),orderReq.getUrl(),orderSave.getId());


    }

    @Override
    public Page<Order> findOrder(OrderSearchReq req, Pageable pageable) {

    Page<Order> orders = orderRepository.searchOrder(req.getProductName(),req.getStatus(),pageable);
    return orders;
    }

    @Override
    public List<Long> getChart() {
        List<Long> orderCount = new ArrayList<Long>();
       for(int i=1;i<=12;i++)
       {
        orderCount.add( orderRepository.countByMonth(i));
       }
       return orderCount;
    }
}
