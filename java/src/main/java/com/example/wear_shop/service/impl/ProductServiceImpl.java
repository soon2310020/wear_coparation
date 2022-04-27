package com.example.wear_shop.service.impl;

import com.example.wear_shop.data.DTO.OrderDTO;
import com.example.wear_shop.data.DTO.ProductOrderReq;
import com.example.wear_shop.data.DTO.ProductOrderRes;
import com.example.wear_shop.data.DTO.fileDTO.FileDto;
import com.example.wear_shop.data.DTO.productDTO.ProductCreateReqDTO;
import com.example.wear_shop.data.DTO.productDTO.ProductReqDTO;
import com.example.wear_shop.data.Entity.Category;
import com.example.wear_shop.data.Entity.Product;
import com.example.wear_shop.repo.CategoryRepository;
import com.example.wear_shop.repo.ProductRepository;
import com.example.wear_shop.service.FileService;
import com.example.wear_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FileService fileService;

    @Override
    public Page<Product> getProduct(ProductReqDTO productReqDTO, Pageable pageable) {


        Page<Product> products = productRepository.searchProductBy(productReqDTO.getName(), productReqDTO.getFromPrice(), productReqDTO.getToPrice(), productReqDTO.getCategoryIds(), pageable);
        return products;
    }

    @Override
    public void create(ProductCreateReqDTO createReqDTO) {
        Product product = new Product();
        product.setId(createReqDTO.getId());
        product.setUpdateAt(Instant.now());
        product.setContent(createReqDTO.getContent());
        Category category = categoryRepository.getById(createReqDTO.getCategoryId());
        product.setCategory(category);
        String files = "";
        if (createReqDTO.getFile() != null) {
            for (Long fId : createReqDTO.getFile()) {
                files += fId + " ";
            }
            product.setFile(files);
        }
        String size = "";
        if (createReqDTO.getSize() != null) {
            for (String s : createReqDTO.getSize()
            ) {
                size += s + " ";
            }
            product.setSize(size);
        }
        product.setPrice(createReqDTO.getPrice());
        product.setTitle(createReqDTO.getTitle());
        product.setDiscount(createReqDTO.getDiscount());
        productRepository.save(product);


    }

    @Override
    public OrderDTO getOrderInfor(List<ProductOrderReq> reqs) throws IOException {
        Long totalPrice = 0L;
        if(reqs.isEmpty()||reqs==null)
        {
            return null;
        }
        OrderDTO orderDetails = new OrderDTO();
        List<ProductOrderRes> productOrderResList = new ArrayList<ProductOrderRes>();
        for (ProductOrderReq req : reqs) {

            ProductOrderRes productOrderRes = new ProductOrderRes();
            Product product = productRepository.getById(req.getId());
            productOrderRes.setId(product.getId());
            productOrderRes.setName(product.getTitle());
            Long rawPrice = product.getPrice() - product.getPrice() * (product.getDiscount() / 100);
            productOrderRes.setPrice(rawPrice);
            if (product.getFile() != null && !product.getFile().isEmpty()) {
                String fileString = product.getFile();
                String[] files = fileString.trim().split(" ");
                Long id = Long.parseLong(files[0]);
                FileDto base64 = fileService.getFileById(id);
                productOrderRes.setFile(base64);
            }
            productOrderRes.setSize(req.getSize());
            productOrderRes.setQuantity(req.getQuality());
            Long price = req.getQuality() * rawPrice;
            productOrderRes.setTotalPrice(price);
            totalPrice += price;
            productOrderResList.add(productOrderRes);

        }
        orderDetails.setProduct(productOrderResList);
        orderDetails.setShipFee(300000L);
        orderDetails.setTotal(totalPrice);
        return orderDetails;
    }
}
