package com.example.wear_shop.repo;

import com.example.wear_shop.data.Entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails,Long> {
}
