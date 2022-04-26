package com.example.wear_shop.repo;

import com.example.wear_shop.data.Entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("select distinct c from Category c where :name = '' or c.name like %:name%")
    Page<Category> getCategoryByName(@Param("name") String name, Pageable pageable);
}
