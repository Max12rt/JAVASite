package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cocktail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String externalId;
    private String name;
    private String image;
    private String taste;
    private String strength;
    private String occasion;
    private String base;
    private String temperature;
    private String serving;
    private String diet;
}

