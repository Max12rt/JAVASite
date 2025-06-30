package com.example.demo.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wine_preferences")
public class WinePreference {

    @Id
    private String id;
    private String sweetness;
    private String body;
    private String alcoholLevel;

    public WinePreference() {}

    public WinePreference(String sweetness, String body, String alcoholLevel) {
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
