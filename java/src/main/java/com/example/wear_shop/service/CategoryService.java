package com.example.wear_shop.service;

import com.example.wear_shop.data.DTO.CategoryDTO.CategoryReqDTO;
import com.example.wear_shop.data.Entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public interface CategoryService  {
    public void create(CategoryReqDTO req);
    public Page<Category> getListCategory(String name, Pageable pageable);
}
