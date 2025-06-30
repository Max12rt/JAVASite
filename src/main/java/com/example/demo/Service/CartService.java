package com.example.demo.Service;

import com.example.demo.Entity.Wine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private List<Wine> cart = new ArrayList<>();

    public void addToCart(Wine wine) {
        cart.add(wine);
    }

    public List<Wine> getCart() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }
}

