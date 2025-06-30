package com.example.demo.controller;

import com.example.demo.Entity.Wine;
import com.example.demo.Service.CartService;
import com.example.demo.Service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private WineService wineService;

    @PostMapping("/add-to-cart")
    public String addToCart(String wineId) {
        Wine wine = wineService.getWineById(wineId);
        cartService.addToCart(wine);
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        return "cart";
    }
}
