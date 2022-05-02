package com.example.wear_shop.repo;

import com.example.wear_shop.data.Entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQueries;
import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("select distinct o from Order o join o.orderDetails od join od.product p where ( :productName = '' or p.title like %:productName%) and " +
            "(coalesce(:status) is null or o.status in :status )")
    public Page<Order> searchOrder(@Param("productName") String productName,
                                   @Param("status") List<Long> status, Pageable pageable);

    @Query(value = "select count (distinct o) from Order o where month(o.createdAt) = :monthParam and" +
            " year(o.createdAt) = year(now()) ")
    public Long countByMonth(@Param("monthParam") int monthParam);

    @Query("select count (o) from Order o where o.status <> 4")
    public Long countAllOrder();
}
