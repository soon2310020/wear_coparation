package com.example.wear_shop.data.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "products")
public class Product {
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
    String avatar;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;




}
