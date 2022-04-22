package com.example.wear_shop.data.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    private String mobile;
    @Column(name = "price_total")
    private Long priceTotal;


}
