package com.example.wear_shop.data.DTO;

import com.example.wear_shop.data.DTO.fileDTO.FileDto;
import lombok.Data;

@Data
public class ProductOrderRes {
    Long id;
    String size;
    Long price;
    Long quantity;
    Long totalPrice;
    FileDto file;
    String name;

}
