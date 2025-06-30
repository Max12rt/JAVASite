package com.example.demo.Service;

import com.example.demo.Entity.Cocktail;
import com.example.demo.Repository.CocktailRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CocktailAPIService {

    private static final String COCKTAIL_LIST_URL = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Alcoholic";
    private static final String COCKTAIL_DETAIL_URL = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=";

    @Autowired
    private CocktailRepository cocktailRepository;

    @Scheduled(cron = "0 0 11 * * ?") // Runs daily at 18:00
    public void fetchAndSaveCocktails() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(COCKTAIL_LIST_URL, String.class);

            if (response != null) {
                JSONObject root = new JSONObject(response);
                JSONArray drinksArray = root.getJSONArray("drinks");
                List<Cocktail> cocktails = new ArrayList<>();

                for (int i = 0; i < drinksArray.length(); i++) {
                    JSONObject drinkJson = drinksArray.getJSONObject(i);
                    String drinkId = drinkJson.getString("idDrink");

                    Thread.sleep(200);

                    String detailUrl = COCKTAIL_DETAIL_URL + drinkId;
                    String detailResponse = restTemplate.getForObject(detailUrl, String.class);

                    if (detailResponse != null) {
                        JSONObject detailRoot = new JSONObject(detailResponse);
                        JSONArray detailedDrinks = detailRoot.getJSONArray("drinks");

                        if (detailedDrinks.length() > 0) {
                            JSONObject detail = detailedDrinks.getJSONObject(0);

                            Cocktail cocktail = new Cocktail();
                            cocktail.setName(detail.optString("strDrink", "Unknown"));
                            cocktail.setImage(detail.optString("strDrinkThumb", ""));
                            cocktail.setExternalId(detail.optString("idDrink", ""));
                            cocktail.setBase(detail.optString("strIngredient1", "Unknown"));
                            cocktail.setTaste("Balanced");
                            cocktail.setStrength("Medium");
                            cocktail.setOccasion("Party");
                            cocktail.setServing("Glass");
                            cocktail.setTemperature("Cold");
                            cocktail.setDiet("Standard");

                            cocktails.add(cocktail);
                        }
                    }
                }

                cocktailRepository.saveAll(cocktails);
                System.out.println("✅ Imported " + cocktails.size() + " cocktails.");
            }

        } catch (Exception e) {
            System.err.println("❌ Error fetching cocktails: " + e.getMessage());
        }
    }
}
