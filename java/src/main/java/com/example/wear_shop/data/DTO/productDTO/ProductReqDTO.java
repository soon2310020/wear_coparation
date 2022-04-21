package com.example.wear_shop.data.DTO.productDTO;

import lombok.Data;

import java.util.List;

@Data
public class ProductReqDTO {
    private String name;
    private Long fromPrice;
    private Long toPrice;
    private List<Long> categoryIds;
}
