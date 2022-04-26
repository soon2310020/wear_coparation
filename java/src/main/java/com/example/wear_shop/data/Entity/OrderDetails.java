package com.example.wear_shop.data.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Table(name = "order_details")
@Entity
@Data
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id",referencedColumnName ="id" )
    private Order order;

    @Column(name = "quatity")
    Long quatity;

}
