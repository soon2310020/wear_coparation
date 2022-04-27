package com.example.wear_shop.data.Entity;

import com.example.wear_shop.data.DTO.fileDTO.FileDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@Table(name = "products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "size")
    String size;


    @Column(name = "price")
    Long price;

    @Column(name = "status")
    Long status;

    @Column(name = "content")
    String content;

    @Column(name = "created_at")
    Instant createAt;

    @Column(name = "updated_at")
    Instant updateAt;


    @Column(name ="file_id")
    String file;

    @Column(name = "discount")
    Long discount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @Transient
    private List<FileDto> base64File;




}
