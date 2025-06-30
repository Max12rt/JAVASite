package com.example.demo.controller;

import com.example.demo.AlcoholPreference;
import com.example.demo.Entity.Wine;
import com.example.demo.Service.OpenAIService;
import com.example.demo.Repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class  WineRecommendationController{

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private WineRepository wineRepository;

    @PostMapping("/recommendation")
    public String getAlcoholRecommendation(@ModelAttribute AlcoholPreference preference, Model model) {
        // Формуємо текст з вподобаннями користувача
        String preferenceText = "Taste: " + preference.getTaste() +
                ", Strength: " + preference.getStrength() +
                ", Occasion: " + preference.getOccasion() +
                ", Base: " + preference.getBase();

        String alcoholRecommendation = openAIService.getWineRecommendation(preferenceText);

        model.addAttribute("wines", alcoholRecommendation);
        return "wine_recommendation_result";
    }


    @GetMapping("/wine_preferences_form")
    public String getAlcoholRecommendationPage() {
        return "wine_preferences_form";
    }

    @GetMapping("/winePreferencesFormBuy")
    public String getAlcoholRecommendationPageBuy() {
        return "winePreferencesFormBuy";
    }
}
