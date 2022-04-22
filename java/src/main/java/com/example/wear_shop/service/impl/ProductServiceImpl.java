package com.example.wear_shop.service.impl;

import com.example.wear_shop.data.DTO.productDTO.ProductCreateReqDTO;
import com.example.wear_shop.data.DTO.productDTO.ProductReqDTO;
import com.example.wear_shop.data.Entity.Category;
import com.example.wear_shop.data.Entity.Product;
import com.example.wear_shop.repo.CategoryRepository;
import com.example.wear_shop.repo.ProductRepository;
import com.example.wear_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Product> getProduct(ProductReqDTO productReqDTO, Pageable pageable) {



        Page<Product> products = productRepository.searchProductBy(productReqDTO.getName(),productReqDTO.getFromPrice(),productReqDTO.getToPrice(),productReqDTO.getCategoryIds(),pageable);
        return products;
    }

    @Override
    public void create(ProductCreateReqDTO createReqDTO) {
        Product product = new Product();
        product.setId(createReqDTO.getId());
        product.setUpdateAt(Instant.now());
        product.setContent(createReqDTO.getContent());
        Category category =categoryRepository.getById(createReqDTO.getCategoryId());
        product.setCategory(category);
        String files ="";
        if (createReqDTO.getFile()!=null)
        {
            for(Long fId : createReqDTO.getFile())
            {
                files += fId+" ";
            }
            product.setFile(files);
        }
        String size ="";
        if (createReqDTO.getSize()!=null)
        {
            for (String s:createReqDTO.getSize()
                 ) {
                size+=s+" ";
            }
            product.setSize(size);
        }
        product.setPrice(createReqDTO.getPrice());
        product.setTitle(createReqDTO.getTitle());
        product.setDiscount(createReqDTO.getDiscount());
        productRepository.save(product);



    }
}
