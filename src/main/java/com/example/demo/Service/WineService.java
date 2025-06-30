package com.example.demo.Service;

import com.example.demo.Entity.Wine;
import com.example.demo.Repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WineService {

    @Autowired
    private WineRepository wineRepository;

    public List<Wine> getAllWines() {
        return wineRepository.findAll();
    }

    public Wine getWineById(String id) {
        Optional<Wine> wine = wineRepository.findById(id);
        return wine.orElse(null);  // Return wine if found, else return null
    }

}

