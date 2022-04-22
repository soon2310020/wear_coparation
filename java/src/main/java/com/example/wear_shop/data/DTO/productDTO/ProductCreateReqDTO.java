package com.example.wear_shop.data.DTO.productDTO;

import com.example.wear_shop.data.Entity.Category;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
public class ProductCreateReqDTO {
    String name;
    Long id;
    String title;
    List<String> size;
    String color;
    Long price;
    String content;
    int status;
    Long avatar;
    List<Long> file;
    Long categoryId;
    Long discount;

}
