package com.example.wear_shop.data.DTO.CategoryDTO;

import lombok.Data;

import java.util.List;

@Data
public class CategoryReqDTO {
    private Long id;
    private String name;
    List<Long> fileId;
    String description;
    int status;
}
