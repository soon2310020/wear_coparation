package com.example.wear_shop.data.DTO.CategoryDTO;

import lombok.Data;

@Data
public class CategoryReqDTO {
    private Long id;
    private String name;
    Long fileId;
    String description;
    int status;
}
