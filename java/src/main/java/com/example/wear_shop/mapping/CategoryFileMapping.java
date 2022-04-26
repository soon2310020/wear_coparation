package com.example.wear_shop.mapping;

import com.example.wear_shop.data.Entity.Category;
import com.example.wear_shop.data.Entity.Product;

import java.io.IOException;
import java.util.List;

public interface CategoryFileMapping {
    void mappingFileForProduct(List<Product> products) throws IOException;
    List<Category> mappingFileForCategory(List<Category> categories) throws IOException;


}
