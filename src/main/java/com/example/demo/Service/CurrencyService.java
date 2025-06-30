package com.example.demo.Service;

import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
    public double getUsdRate() {
        // Можна зробити запит до https://api.privatbank.ua або НБУ
        return 39.2; // мокове значення
    }
}

