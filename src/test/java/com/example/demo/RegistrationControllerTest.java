package com.example.demo;


import com.example.demo.Entity.MyAppUser;
import com.example.demo.Repository.MyAppUserRepository;
import com.example.demo.controller.RegistrationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRegisterNewUser() throws Exception {
        String newUser = """
            {
              "username": "testuser",
              "email": "test@example.com",
              "password": "testpass123"
            }
            """;

        mockMvc.perform(post("/req/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }
}
@SpringBootTest
@AutoConfigureMockMvc
class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyAppUserRepository repository;

    @BeforeEach
    void setup() {
        MyAppUser user = new MyAppUser();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin123"));
        repository.save(user);
    }

    @Test
    void shouldAuthenticateWithValidCredentials() throws Exception {
        mockMvc.perform(formLogin("/login").user("test").password("1234"))
                .andExpect(authenticated());
    }

    @Test
    void shouldFailWithInvalidCredentials() throws Exception {
        mockMvc.perform(formLogin("/login").user("admin").password("wrongpass"))
                .andExpect(unauthenticated());
    }
}
