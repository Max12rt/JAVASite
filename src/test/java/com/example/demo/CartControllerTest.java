package com.example.demo;


import com.example.demo.Entity.Wine;
import com.example.demo.Service.CartService;
import com.example.demo.Service.WineService;
import com.example.demo.controller.CartController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private WineService wineService;

    @Test
    void testAddToCart() throws Exception {
        Wine mockWine = new Wine();  // add setters if necessary
        when(wineService.getWineById("123")).thenReturn(mockWine);

        mockMvc.perform(post("/add-to-cart").param("wineId", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/shop"));

        verify(cartService, times(1)).addToCart(mockWine);
    }

    @Test
    void testViewCart() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attributeExists("cart"));
    }
}

