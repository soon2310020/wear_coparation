package com.example.wear_shop.controller;

import com.example.wear_shop.data.DTO.CategoryDTO.CategoryReqDTO;
import com.example.wear_shop.data.Entity.Category;
import com.example.wear_shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/category")
    public void createCategory(@RequestBody @Valid CategoryReqDTO req,@RequestParam("files") MultipartFile[] files)
    {
        MultipartFile[] files1=files;
        categoryService.create(req);
    }

    @GetMapping("/category")
    public ResponseEntity<Page<Category>> getListCategory(@RequestParam(required = false) String name, Pageable pageable)
    {
       Page<Category> categories = categoryService.getListCategory(name,pageable);
       return ResponseEntity.ok(categories);
    }


}
