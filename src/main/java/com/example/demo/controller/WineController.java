package com.example.demo.controller;

import com.example.demo.Entity.Wine;
import com.example.demo.Service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WineController {

    @Autowired
    private WineService wineService;

    @GetMapping("/shop")
    public String shop(Model model) {
        List<Wine> wines = wineService.getAllWines();
        model.addAttribute("wines", wines);
        return "shop";
    }
}

