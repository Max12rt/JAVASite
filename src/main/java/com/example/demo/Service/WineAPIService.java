package com.example.demo.Service;

import com.example.demo.Entity.Wine;
import com.example.demo.Repository.WineRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;

@Service
public class WineAPIService {

    private static final String WINE_API_URL = "https://api.sampleapis.com/wines/reds";
    Random rand = new Random();


    @Autowired
    private WineRepository wineRepository;

    @Scheduled(cron = "0 0 10 * * ?") // Запуск щодня о 10:00 ранку
    public void fetchAndSaveWines() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(WINE_API_URL, String.class);

            if (response != null) {
                JSONArray jsonArray = new JSONArray(response);
                List<Wine> wines = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject wineJson = jsonArray.getJSONObject(i);
                    Wine wine = new Wine();

                    try {
                        wine.setName(wineJson.getString("wine"));
                        wine.setDescription(wineJson.optString("winery", "No description available"));
                        wine.setPrice(wineJson.optDouble("rating", 20.0) * rand.nextInt(10000));

                        wines.add(wine);
                    } catch (JSONException e) {
                        System.err.println("Помилка при обробці JSON для вина №" + i);
                    }
                }

                wineRepository.saveAll(wines);
                System.out.println("✅ Додано " + wines.size() + " нових вин.");
            }

        } catch (Exception e) {
            System.err.println("Помилка при отриманні вин: " + e.getMessage());
        }
    }
}
