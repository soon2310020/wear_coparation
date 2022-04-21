package com.example.wear_shop.repo;

import com.example.wear_shop.data.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {
    @Query("select distinct p from Product p inner join p.category c where" +
            " (coalesce(:cId) is null or c.id in :cId) and" +
            " (:fromPrice is null or p.price >= :fromPrice) and " +
            " (:toPrice is null or p.price <= :toPrice) and " +
            " (:name is null or p.name = :name)")
    Page<Product> searchProductBy(@Param("name") String name,
                                  @Param("fromPrice") Double fromPrice,
                                  @Param("toPrice") Double toPrice,
                                  @Param("cId") List<Long> category,
                                  Pageable pageable);
}
