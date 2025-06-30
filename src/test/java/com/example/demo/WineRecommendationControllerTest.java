package com.example.demo;

import com.example.demo.Entity.Wine;
import com.example.demo.configuration.RestTemplateConfig;
import com.example.demo.Entity.WinePreference;
import com.example.demo.Repository.WineRepository;
import com.example.demo.Service.OpenAIService;
import com.example.demo.Service.WineAIService;
import com.example.demo.controller.WineRecommendationController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(WineRecommendationController.class)
public class WineRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpenAIService openAIService;

    @MockBean
    private WineRepository wineRepository;

    @Test
    @WithMockUser // Додаємо фейкового авторизованого користувача
    void testGetWineRecommendation() throws Exception {
        when(openAIService.getWineRecommendation(anyString()))
                .thenReturn("Ось ваш напій: Біле ігристе вино");

        mockMvc.perform(post("/recommendation")
                        .param("taste", "Солодкий")
                        .param("strength", "Середній")
                        .param("occasion", "Вечірка")
                        .param("base", "Вино")
                        .with(csrf())) // додаємо CSRF токен
                .andExpect(status().isOk())
                .andExpect(view().name("wine_recommendation_result"))
                .andExpect(model().attributeExists("wines"))
                .andExpect(model().attribute("wines", "Ось ваш напій: Біле ігристе вино"));
    }
}
@ExtendWith(MockitoExtension.class)
class OpenAIServiceTest {

    @InjectMocks
    private OpenAIService openAIService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void testGetWineRecommendation_successful() throws JSONException {
        // Тестовий рядок переваг
        String preferences = "Taste: sweet, Strength: light, Occasion: chill, Base: wine";

        // Мокований вибір із відповіді
        JSONObject choice = new JSONObject()
                .put("message", new JSONObject().put("content", "Rosé"));

        // Мокований JSON-відповідь від API
        JSONObject response = new JSONObject()
                .put("choices", new JSONArray().put(choice));

        // Мокована ResponseEntity
        ResponseEntity<String> mockResponse = new ResponseEntity<>(response.toString(), HttpStatus.OK);



        // Виклик методу, який тестуємо
        String result = openAIService.getWineRecommendation(preferences);

        // Перевірка, чи отримано правильну рекомендацію
        assertEquals("Rosé", result);
    }
}


class WineAIServiceTest {

    @Mock
    private WineRepository wineRepository;

    @InjectMocks
    private WineAIService wineAIService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRecommendWine_returnsMatchingWines() {
        WinePreference pref = new WinePreference("sweet", "full", "medium");

        List<Wine> mockWines = List.of(
                new Wine("Wine A", "Desc", 20.0, "sweet", "full", "medium"),
                new Wine("Wine B", "Desc", 22.0, "dry", "light", "high")
        );

        when(wineRepository.findAll()).thenReturn(mockWines);

        List<String> result = wineAIService.recommendWine(pref);
        assertEquals(1, result.size());
        assertTrue(result.contains("Wine A"));
    }
}



@SpringBootTest
@AutoConfigureMockMvc
class LoadTest3 {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoadUnderStress() throws Exception {
        for (int i = 0; i < 100; i++) {
            mockMvc.perform(post("/recommendation")
                            .param("taste", "sweet")
                            .param("strength", "light")
                            .param("occasion", "chill")
                            .param("base", "wine"))
                    .andExpect(status().isOk());
        }
    }
    @Test
    void testChat_ReturnsValidResponse() throws Exception {
        ChatResponse chatResponse = new ChatResponse();
        Message message = new Message("assistant", "Mojito");
        ChatResponse.Choice choice = new ChatResponse.Choice();
        choice.setMessage(message);
        chatResponse.setChoices(List.of(choice));

        //when(restTemplate.postForObject(anyString(), any(HttpEntity.class), any()))
                //.thenReturn(chatResponse);

        mockMvc.perform(post("/chat")
                        .with(user("testuser").password("password").roles("USER")) // <--- ДОДАНО
                        .param("taste", "sweet")
                        .param("strength", "light")
                        .param("occasion", "chill")
                        .param("base", "rum")
                        .param("temperature", "cold")
                        .param("serving", "cocktail")
                        .param("diet", "none")
                        .param("ingredients", "mint"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attributeExists("response"));
    }
}
@SpringBootTest
@AutoConfigureMockMvc
class ChatControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "openaiRestTemplate")
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Test
    void testChat_ReturnsValidResponse() throws Exception {
        // Підготовка мок-відповіді від OpenAI
        ChatResponse chatResponse = new ChatResponse();
        Message message = new Message("assistant", "Mojito");
        ChatResponse.Choice choice = new ChatResponse.Choice();
        choice.setMessage(message);
        chatResponse.setChoices(List.of(choice));

        // Мокування RestTemplate
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), any()))
                .thenReturn(chatResponse);

        // Виконання тесту контролера
        mockMvc.perform(post("/chat")
                        .param("taste", "sweet")
                        .param("strength", "light")
                        .param("occasion", "chill")
                        .param("base", "rum")
                        .param("temperature", "cold")
                        .param("serving", "cocktail")
                        .param("diet", "none")
                        .param("ingredients", "mint"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attributeExists("response"));
    }
}
@SpringBootTest
@AutoConfigureMockMvc
class WineRecommendationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpenAIService openAIService;

    @Test
    void testGetRecommendation_ReturnsRecommendationPage() throws Exception {
        when(openAIService.getWineRecommendation(anyString())).thenReturn("Chardonnay");

        mockMvc.perform(post("/recommendation")
                        .param("taste", "sweet")
                        .param("strength", "light")
                        .param("occasion", "party")
                        .param("base", "wine"))
                .andExpect(status().isOk())
                .andExpect(view().name("wine_recommendation_result"))
                .andExpect(model().attribute("wines", "Chardonnay"));
    }

    @Test
    void testGetFormPages() throws Exception {
        mockMvc.perform(get("/wine_preferences_form"))
                .andExpect(status().isOk())
                .andExpect(view().name("wine_preferences_form"));
    }
}



