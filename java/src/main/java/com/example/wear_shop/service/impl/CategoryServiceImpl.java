package com.example.wear_shop.service.impl;

import com.example.wear_shop.data.DTO.CategoryDTO.CategoryReqDTO;
import com.example.wear_shop.data.Entity.Category;
import com.example.wear_shop.repo.CategoryRepository;
import com.example.wear_shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public void create(CategoryReqDTO req)
    {
        Category category = new Category();
        category.setId(req.getId());
        category.setCreateAt(Instant.now());
        category.setUpdateAt(Instant.now());
        category.setName(req.getName());
        category.setDescription(req.getDescription());
        if (req.getFileId()!=null||!req.getFileId().isEmpty())
        {
            String fileIds ="";

            for (Long fileId:req.getFileId()
                 ) {
                fileIds+=fileId+" ";
            }
            category.setFile(fileIds);
        }
        categoryRepository.save(category);
    }

    @Override
    public Page<Category> getListCategory(String name, Pageable pageable) {
        Page<Category> categories = categoryRepository.getCategoryByName(name,pageable );
        for (Category c:categories.getContent())
        {
            c.setCreateAt(c.getCreateAt());
        }
        return categories;
    }
}
