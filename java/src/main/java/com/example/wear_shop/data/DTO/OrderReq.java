package com.example.wear_shop.data.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderReq {
    private List<ProductOrderReq> products;
    private String address;
    private String phoneNumber;
    private String ReceivedName;
    private Long userId;
    private String url;
}
