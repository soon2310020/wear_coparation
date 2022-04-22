package com.example.wear_shop.data.Entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "order_details")
@Entity
@Data
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    Product product;
}
