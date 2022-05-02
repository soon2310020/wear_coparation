package com.example.wear_shop.data.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "news")
public class New {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String content;


    @Column(name = "created_at")
    Instant createAt;

    @Column(name = "updated_at")
    Instant updateAt;

}
