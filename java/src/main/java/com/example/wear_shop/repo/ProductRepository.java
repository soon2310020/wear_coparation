package com.example.wear_shop.repo;

import com.example.wear_shop.data.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {
    @Query("select distinct p from Product p inner join p.category c where " +
            " (coalesce(:cId) is null or c.id in :cId ) and " +
            " ( :name is null or p.title like %:name%) and " +
            " ( :toPrice is null or p.price <= :toPrice) and " +
             "( :fromPrice is null or p.price >= :fromPrice)")
    Page<Product> searchProductBy(@Param("name") String name,
                                  @Param("fromPrice") Long fromPrice,
                                  @Param("toPrice") Long toPrice,
                                  @Param("cId") List<Long> category,
                                  Pageable pageable);
    @Query("select distinct p from Product p inner join p.category c where " +
            " (coalesce(:cId) is null or c.id in :cId ) and " +
            " ( :name is null or p.title like %:name%) and " +
            " ( :toPrice is null or p.price <= :toPrice) and " +
            "( :fromPrice is null or p.price >= :fromPrice) and p.status <> 0")
    Page<Product> searchProductForFrontEndBy(@Param("name") String name,
                                  @Param("fromPrice") Long fromPrice,
                                  @Param("toPrice") Long toPrice,
                                  @Param("cId") List<Long> category,
                                  Pageable pageable);
    @Query("select count( distinct p) from Product p where p.status <> 0")
    Long countAllByStatus();
}
