package com.example.wear_shop.data.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderSearchReq {
    private String productName;
    private List<Long> status;
}
