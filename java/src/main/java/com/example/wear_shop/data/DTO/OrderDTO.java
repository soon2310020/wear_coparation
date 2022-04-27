package com.example.wear_shop.data.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private List<ProductOrderRes> product;
    Long subTotalPrice;
    Long shipFee;
    Long total;
}
