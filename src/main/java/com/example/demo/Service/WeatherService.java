package com.example.demo.Service;

import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    public String getCurrentWeather() {
        // Можна зробити HTTP-запит до OpenWeatherMap і спарсити температуру
        // Але поки що — просто імітація:
        return "sunny"; // або "cold", "hot", "rainy"
    }
}

