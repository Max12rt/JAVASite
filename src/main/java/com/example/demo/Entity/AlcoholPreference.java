package com.example.demo.Entity;

public class AlcoholPreference {
    private String sweetness;
    private String body;
    private String alcoholLevel;

    public AlcoholPreference() {}

    public AlcoholPreference(String sweetness, String body, String alcoholLevel) {
        this.sweetness = sweetness;
        this.body = body;
        this.alcoholLevel = alcoholLevel;
    }

    public String getSweetness() { return sweetness; }
    public void setSweetness(String sweetness) { this.sweetness = sweetness; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public String getAlcoholLevel() { return alcoholLevel; }
    public void setAlcoholLevel(String alcoholLevel) { this.alcoholLevel = alcoholLevel; }
}


