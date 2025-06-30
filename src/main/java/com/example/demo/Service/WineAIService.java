package com.example.demo.Service;

import com.example.demo.Entity.Wine;
import com.example.demo.Entity.WinePreference;
import com.example.demo.Repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WineAIService {

    @Autowired
    private WineRepository wineRepository;

    public List<String> recommendWine(WinePreference preference) {
        List<Wine> allWines = wineRepository.findAll();

        return allWines.stream()
                .filter(wine -> wine.getSweetness().equals(preference.getSweetness()))
                .filter(wine -> wine.getBody().equals(preference.getBody()))
                .filter(wine -> wine.getAlcoholLevel().equals(preference.getAlcoholLevel()))
                .map(Wine::getName)
                .collect(Collectors.toList());
    }
}

