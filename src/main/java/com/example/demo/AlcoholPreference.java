package com.example.demo;

public class AlcoholPreference {
    private String taste;
    private String strength;
    private String occasion;
    private String base;
    private String temperature;
    private String serving;
    private String diet;
    private String ingredients;

    private int age;
    private String sex;

    // Getters and setters
    public String getTaste() { return taste; }
    public void setTaste(String taste) { this.taste = taste; }

    public String getStrength() { return strength; }
    public void setStrength(String strength) { this.strength = strength; }

    public String getOccasion() { return occasion; }
    public void setOccasion(String occasion) { this.occasion = occasion; }

    public String getBase() { return base; }
    public void setBase(String base) { this.base = base; }

    public String getTemperature() { return temperature; }
    public void setTemperature(String temperature) { this.temperature = temperature; }

    public String getServing() { return serving; }
    public void setServing(String serving) { this.serving = serving; }

    public String getDiet() { return diet; }
    public void setDiet(String diet) { this.diet = diet; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
