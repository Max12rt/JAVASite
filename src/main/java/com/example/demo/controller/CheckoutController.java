package com.example.demo.controller;

import com.example.demo.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @PostMapping("/checkout")
    public String checkout() {
        // Process the order here (e.g., save to a database)
        cartService.clearCart();
        return "redirect:/shop";
    }
}

