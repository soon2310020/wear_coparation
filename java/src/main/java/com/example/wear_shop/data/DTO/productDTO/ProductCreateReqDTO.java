package com.example.wear_shop.data.DTO.productDTO;

import com.example.wear_shop.data.Entity.Category;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
public class ProductCreateReqDTO {
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "size")
    String size;

    @Column(name = "color")
    String color;

    @Column(name = "price")
    Double price;

    @Column(name = "status")
    int status;

    @Column(name = "created_at")
    Instant createAt;

    @Column(name = "updated_at")
    Instant updateAt;

    @Column(name = "avatar")
    Long avatar;

    @Column(name ="file_id")
    String file;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

}
