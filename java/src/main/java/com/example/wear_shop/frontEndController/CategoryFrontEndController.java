package com.example.wear_shop.frontEndController;

import com.example.wear_shop.data.DTO.CategoryDTO.CategoryReqDTO;
import com.example.wear_shop.data.Entity.Category;
import com.example.wear_shop.mapping.CategoryFileMapping;
import com.example.wear_shop.repo.CategoryRepository;
import com.example.wear_shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/front-end/categories")
public class CategoryFrontEndController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryFileMapping categoryFileMapping;

    @GetMapping("/category")

    public ResponseEntity<Page<Category>> getListCategory(@RequestParam(required = false) String name, Pageable pageable)
    {
        Page<Category> categories = categoryService.getListCategory(name,pageable);
        categoryFileMapping.mappingFileForCategory(categories.getContent());

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/category/{id}")

    public ResponseEntity<Category> getCategory(@PathVariable(required = true) Long id)
    {
        Category category =categoryRepository.getById(id);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(required = true) Long id)
    {
        Category category =categoryRepository.getById(id);
        categoryRepository.delete(category);
        return ResponseEntity.ok("Success");
    }


}
