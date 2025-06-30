package com.example.demo.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wines")
public class Wine {
    @Id
    private String id;
    private String name;
    private String type;
    private double price;
    private String imageUrl;

    private String description;

    private String sweetness;
    private String body;
    private String alcoholLevel;

    public Wine() {}

    public Wine(String name, String description, double price, String sweetness, String body, String alcoholLevel) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sweetness = sweetness;
        this.body = body;
        this.alcoholLevel = alcoholLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public String getDescription() {
        return description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getSweetness() {
        return sweetness;
    }

    public void setSweetness(String sweetness) {
        this.sweetness = sweetness;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAlcoholLevel() {
        return alcoholLevel;
    }

    public void setAlcoholLevel(String alcoholLevel) {
        this.alcoholLevel = alcoholLevel;
    }
}
