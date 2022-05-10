package com.example.wear_shop.frontEndController;

import com.example.wear_shop.data.DTO.OrderDTO;
import com.example.wear_shop.data.DTO.ProductOrderReq;
import com.example.wear_shop.data.DTO.productDTO.ProductCreateReqDTO;
import com.example.wear_shop.data.DTO.productDTO.ProductReqDTO;
import com.example.wear_shop.data.Entity.Product;
import com.example.wear_shop.mapping.CategoryFileMapping;
import com.example.wear_shop.repo.ProductRepository;
import com.example.wear_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/front-end/product")
public class ProductFrontEndController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryFileMapping categoryFileMapping;

    @PostMapping("/search-product")
    public ResponseEntity<Page<Product>> searchProduct(@RequestBody ProductReqDTO req, Pageable pageable) throws IOException {
        Page<Product> products = productService.getProductForfrontEnd(req,pageable);
        categoryFileMapping.mappingFileForProduct(products.getContent());
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")

    public void deleteProduct(@PathVariable("id") Long id) throws Exception {
        Product product = productRepository.getById(id);
        productRepository.delete(product);
        if (product==null)
        {
            throw new Exception();
        }
    }
    @GetMapping("/{id}/{size}")
    public ResponseEntity<Product> getProduct(@PathVariable(required = false,name = "size")String size, @PathVariable(name = "id") Long id)
    {
        Product product = productRepository.getById(id);
        if (!size.isEmpty()&& Objects.isNull(product))
        {
            product.setSize(size);
        }
        return ResponseEntity.ok(product);
    }
    @GetMapping("/product")
    public ResponseEntity<Product> getProduct(@RequestParam("id") Long id) throws IOException {
        Product product = productRepository.getById(id);
        categoryFileMapping.mappingFileForProduct(product);

        return ResponseEntity.ok(product);
    }
    @PostMapping("/order")
    public ResponseEntity<OrderDTO> getOrderInfor(@RequestBody(required = false) List<ProductOrderReq> req) throws IOException {
        OrderDTO orderDTO=productService.getOrderInfor(req);
        return ResponseEntity.ok(orderDTO);
    }


}
