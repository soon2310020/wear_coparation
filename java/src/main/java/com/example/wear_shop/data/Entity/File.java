package com.example.wear_shop.data.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "file_name")
    String name;

    @Column(name = "direct")
    String direct;

}
