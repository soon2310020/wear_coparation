package com.example.wear_shop.controller;

import com.example.wear_shop.data.DTO.productDTO.ProductCreateReqDTO;
import com.example.wear_shop.data.DTO.productDTO.ProductReqDTO;
import com.example.wear_shop.data.Entity.Product;
import com.example.wear_shop.repo.ProductRepository;
import com.example.wear_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/product")
    public void createProduct(@RequestBody ProductCreateReqDTO product)
    {
       productService.create(product);
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/search-product")
    public ResponseEntity<Page<Product>> searchProduct(@RequestBody ProductReqDTO req, Pageable pageable)
    {
        Page<Product> products = productService.getProduct(req,pageable);
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")

    public void deleteProduct(@PathVariable("id") Long id) throws Exception {
        Product product = productRepository.getById(id);
        if (product==null)
        {
            throw new Exception();
        }
    }


}
