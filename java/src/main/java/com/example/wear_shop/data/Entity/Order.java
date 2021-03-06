package com.example.wear_shop.data.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    @Column(name = "payment_status")
    private Long status;

    @Column(name = "fullname")
    private String receivedName;

    @OneToMany(mappedBy = "order",cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.DETACH})
    private List<OrderDetails> orderDetails;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;


}
