/*package com.example.demo;

import com.example.demo.Service.OpenAIService;
import com.example.demo.Service.WeatherService;
import com.example.demo.Service.CurrencyService;
import com.example.demo.Repository.WineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(org.springframework.extension.junit5.SpringExtension.class)
public class ChatControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ChatController chatController;

    @Mock
    private WeatherService weatherService;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private WineRepository wineRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(chatController).build();
    }

    @Test
    public void testChat() throws Exception {
        AlcoholPreference alcoholPreference = new AlcoholPreference();
        alcoholPreference.setTaste("sweet");
        alcoholPreference.setStrength("medium");
        alcoholPreference.setBase("vodka");
        alcoholPreference.setTemperature("cold");
        alcoholPreference.setServing("cocktail");
        alcoholPreference.setDiet("none");
        alcoholPreference.setOccasion("party");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/chat")
                        .flashAttr("alcoholPreference", alcoholPreference))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("result"));
    }

    @Test
    public void testChatBuy() throws Exception {
        AlcoholPreference alcoholPreference = new AlcoholPreference();
        alcoholPreference.setTaste("bitter");
        alcoholPreference.setStrength("strong");
        alcoholPreference.setBase("rum");
        alcoholPreference.setTemperature("room");
        alcoholPreference.setServing("rocks");
        alcoholPreference.setDiet("gluten_free");
        alcoholPreference.setOccasion("celebration");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/chatBuy")
                        .flashAttr("alcoholPreference", alcoholPreference))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("resultForBuy"));
    }

    @Test
    public void testForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("wine_preferences_form"));
    }
}

class OpenAIServiceTest {

    @InjectMocks
    private OpenAIService openAIService;

    @Mock
    private RestTemplate restTemplate;

    @Value("${openai.api.key}")
    private String apiKey;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(openAIService, "apiKey", "mock-api-key"); // Fake key for testing
    }

    @Test
    void testGetWineRecommendation_Success() {
        String preferences = "Sweet, Strong, Party";
        String responseJson = "{ \"choices\": [{ \"message\": { \"content\": \"Mojito\" } }] }";
        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(responseJson, HttpStatus.OK));

        String recommendation = openAIService.getWineRecommendation(preferences);

        assertNotNull(recommendation);
        assertEquals("Mojito", recommendation);
    }

    @Test
    void testGetWineRecommendation_NoRecommendation() {
        String preferences = "Bitter, Light, Chill";
        String responseJson = "{ \"choices\": [] }";
        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(responseJson, HttpStatus.OK));

        String recommendation = openAIService.getWineRecommendation(preferences);

        assertNotNull(recommendation);
        assertEquals("No recommendation found", recommendation);
    }

    @Test
    void testGetWineRecommendation_Error() {
        String preferences = "Sour, Strong, Romantic";
        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
                .thenThrow(new RuntimeException("API Error"));

        String recommendation = openAIService.getWineRecommendation(preferences);

        assertNotNull(recommendation);
        assertEquals("Error processing recommendation", recommendation);
    }
}
*/
package com.example.demo;

import com.example.demo.Service.WeatherService;
import com.example.demo.Service.CurrencyService;
import com.example.demo.Repository.WineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension; // Import SpringExtension

@ExtendWith(SpringExtension.class)  // Use SpringExtension for Spring context in JUnit 5
public class ChatControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ChatController chatController;

    @Mock
    private WeatherService weatherService;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private WineRepository wineRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(chatController).build();
    }

    @Test
    public void testChat() throws Exception {
        AlcoholPreference alcoholPreference = new AlcoholPreference();
        alcoholPreference.setTaste("sweet");
        alcoholPreference.setStrength("medium");
        alcoholPreference.setBase("vodka");
        alcoholPreference.setTemperature("cold");
        alcoholPreference.setServing("cocktail");
        alcoholPreference.setDiet("none");
        alcoholPreference.setOccasion("party");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/chat")
                        .flashAttr("alcoholPreference", alcoholPreference))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("result"));
    }

    @Test
    public void testChatBuy() throws Exception {
        AlcoholPreference alcoholPreference = new AlcoholPreference();
        alcoholPreference.setTaste("bitter");
        alcoholPreference.setStrength("strong");
        alcoholPreference.setBase("rum");
        alcoholPreference.setTemperature("room");
        alcoholPreference.setServing("rocks");
        alcoholPreference.setDiet("gluten_free");
        alcoholPreference.setOccasion("celebration");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/chatBuy")
                        .flashAttr("alcoholPreference", alcoholPreference))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("resultForBuy"));
    }

    @Test
    public void testForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("wine_preferences_form"));
    }
}
