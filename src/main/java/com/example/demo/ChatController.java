/*package com.example.demo;

import com.example.demo.Entity.MyAppUser;
import com.example.demo.Entity.Wine;
import com.example.demo.Entity.WinePreference;
import com.example.demo.Entity.AlcoholPreferenceEntity;
import com.example.demo.Repository.AlcoholPreferenceRepository;
import com.example.demo.Repository.MyAppUserRepository;
import com.example.demo.Repository.WineRepository;
import com.example.demo.Service.WeatherService;
import com.example.demo.Service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChatController {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.model2}")
    private String model2;

    @Value("${openai.model3}")
    private String model3;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WineRepository wineRepository;

    @Autowired
    private AlcoholPreferenceRepository alcoholPreferenceEntryRepository;

    @Autowired
    private MyAppUserRepository userRepository;

    @PostMapping("/chat")
    public String chat(@ModelAttribute AlcoholPreference preference, Model modelAttr) {

        // Формуємо prompt з усіх вподобань
        String prompt = "I want a drink with the following preferences:\n" +
                "Taste: " + preference.getTaste() + "\n" +
                "Strength: " + preference.getStrength() + "\n" +
                "Occasion: " + preference.getOccasion() + "\n" +
                "Base: " + preference.getBase() + "\n" +
                "Temperature: " + preference.getTemperature() + "\n" +
                "Serving: " + preference.getServing() + "\n" +
                "Diet: " + preference.getDiet() + "\n" +
                "Ingredients: " + preference.getIngredients()+ "answer need in ukrain";

        ChatRequest request = new ChatRequest(model, prompt);
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            modelAttr.addAttribute("response", "No response from OpenAI.");
        } else {
            String reply = response.getChoices().get(0).getMessage().getContent();
            modelAttr.addAttribute("response", reply);
        }

        return "result";
    }

    @PostMapping("/chatBuy")
    public String chatConsensus(@ModelAttribute AlcoholPreference preference, Model modelAttr) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        MyAppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AlcoholPreferenceEntity entry = new AlcoholPreferenceEntity(user.getId(), preference);
        alcoholPreferenceEntryRepository.save(entry);


        List<AlcoholPreferenceEntity> previousPreferences = alcoholPreferenceEntryRepository.findAllByUserId(user.getId());


        StringBuilder combinedPrompt = new StringBuilder("Я хочу напій, враховуючи мої попередні вподобання:\n");


        for (AlcoholPreferenceEntity prev : previousPreferences) {
            AlcoholPreference p = prev.getPreference();
            combinedPrompt.append("- Смак: ").append(p.getTaste()).append(", ")
                    .append("Міцність: ").append(p.getStrength()).append(", ")
                    .append("Нагода: ").append(p.getOccasion()).append(", ")
                    .append("База: ").append(p.getBase()).append(", ")
                    .append("Температура: ").append(p.getTemperature()).append(", ")
                    .append("Подача: ").append(p.getServing()).append(", ")
                    .append("Дієта: ").append(p.getDiet()).append(", ")
                    .append("Інгредієнти: ").append(p.getIngredients()).append("\n");
        }

        combinedPrompt.append("Відповідь українською, будь ласка.");

        String drinkName = "";

        while (true) {
            // 1. Отримуємо три відповіді
            ChatRequest request1 = new ChatRequest(model, basePrompt);
            ChatRequest request2 = new ChatRequest(model2, basePrompt);
            ChatRequest request3 = new ChatRequest(model3, basePrompt);

            ChatResponse res1 = restTemplate.postForObject(apiUrl, request1, ChatResponse.class);
            ChatResponse res2 = restTemplate.postForObject(apiUrl, request2, ChatResponse.class);
            ChatResponse res3 = restTemplate.postForObject(apiUrl, request3, ChatResponse.class);

            String reply1 = getReplyOrDefault(res1);
            String reply2 = getReplyOrDefault(res2);
            String reply3 = getReplyOrDefault(res3);

            // 2. Оцінюємо яка відповідь краща
            String evaluationPrompt = "Є три варіанти:\n" +
                    "1: " + reply1 + "\n" +
                    "2: " + reply2 + "\n" +
                    "3: " + reply3 + "\n" +
                    "Оберіть кращу відповідь. У результаті має бути лише назва напою (1-2 слова) без жодного пояснення.";

            ChatRequest judgeRequest = new ChatRequest(model2, evaluationPrompt);
            ChatResponse judgeResponse = restTemplate.postForObject(apiUrl, judgeRequest, ChatResponse.class);
            String finalDecision = getReplyOrDefault(judgeResponse);

            // 3. Перевіряємо чи це справді тільки назва напою
            String validationPrompt = "Чи є цей текст \"" + finalDecision + "\" лише назвою алкогольного напою (без опису, без речень)? " +
                    "Відповідай лише 'yes' або 'no'.";

            ChatRequest validationRequest = new ChatRequest(model2, validationPrompt);
            ChatResponse validationResponse = restTemplate.postForObject(apiUrl, validationRequest, ChatResponse.class);
            String validationResult = getReplyOrDefault(validationResponse).toLowerCase().trim();

            if (validationResult.contains("yes")) {
                drinkName = finalDecision;
                break;
            }
        }

        String drinkSearchName = drinkName.trim().replace(" ", "+");
        String searchUrl = "https://winetime.com.ua/search?q=" + drinkSearchName;

        modelAttr.addAttribute("response", "Ваш напій: " + drinkName);
        modelAttr.addAttribute("shopLink", searchUrl);

        return "resultForBuy";
    }


    private String getReplyOrDefault(ChatResponse response) {
        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
            return response.getChoices().get(0).getMessage().getContent();
        }
        return "No response";
    }

    @GetMapping("/")
    public String form(Model model) {
        model.addAttribute("alcoholPreference", new AlcoholPreference());
        return "wine_preferences_form";
    }
}
*/
package com.example.demo;

import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
import com.example.demo.Service.CurrencyService;
import com.example.demo.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class ChatController {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.model2}")
    private String model2;

    @Value("${openai.model3}")
    private String model3;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WineRepository wineRepository;

    @Autowired
    private AlcoholPreferenceRepository alcoholPreferenceRepository;

    @Autowired
    private MyAppUserRepository userRepository;

    @GetMapping("/")
    public String form(Model model) {
        model.addAttribute("alcoholPreference", new AlcoholPreference());
        return "wine_preferences_form";
    }

    @PostMapping("/chat")
    public String chat(@ModelAttribute AlcoholPreference preference, Model modelAttr) {
        String prompt = buildPrompt(preference);
        ChatRequest request = new ChatRequest(model, prompt);
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        String reply = getReplyOrDefault(response);
        modelAttr.addAttribute("response", reply);

        return "result";
    }

    @PostMapping("/chatBuy")
    public String chatConsensus(@ModelAttribute AlcoholPreference preference, Model modelAttr) {
        // Отримання користувача
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        MyAppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Збереження поточних вподобань
        AlcoholPreferenceEntity entry = new AlcoholPreferenceEntity(user.getId(), preference);
        alcoholPreferenceRepository.save(entry);

        // Отримання всіх вподобань
        List<AlcoholPreferenceEntity> preferences = alcoholPreferenceRepository.findAllByUserId(user.getId());
        String combinedPrompt = buildCombinedPrompt(preferences);

        // Визначення найкращої відповіді
        String drinkName = selectBestDrinkName(combinedPrompt);

        // Формування результату
        String searchUrl = "https://winetime.com.ua/search?q=" + drinkName.trim().replace(" ", "+");
        modelAttr.addAttribute("response", "Ваш напій: " + drinkName);
        modelAttr.addAttribute("shopLink", searchUrl);

        return "resultForBuy";
    }

    private String buildPrompt(AlcoholPreference p) {
        return String.format("""
            I want a drink with the following preferences:
            Age: %d
            Sex: %s
            Taste: %s
            Strength: %s
            Occasion: %s
            Base: %s
            Temperature: %s
            Serving: %s
            Diet: %s
            Ingredients: %s
            answer need in ukrainian
            """,
                p.getAge(), p.getSex(), p.getTaste(), p.getStrength(), p.getOccasion(),
                p.getBase(), p.getTemperature(), p.getServing(), p.getDiet(), p.getIngredients()
        );
    }


    private String buildCombinedPrompt(List<AlcoholPreferenceEntity> preferences) {
        StringBuilder sb = new StringBuilder("Я хочу напій, враховуючи мої попередні вподобання:\n");
        for (AlcoholPreferenceEntity entity : preferences) {
            AlcoholPreference p = entity.getPreference();
            sb.append("- Вік: ").append(p.getAge())
                    .append(", Стать: ").append(p.getSex())
                    .append(", Смак: ").append(p.getTaste())
                    .append(", Міцність: ").append(p.getStrength())
                    .append(", Нагода: ").append(p.getOccasion())
                    .append(", База: ").append(p.getBase())
                    .append(", Температура: ").append(p.getTemperature())
                    .append(", Подача: ").append(p.getServing())
                    .append(", Дієта: ").append(p.getDiet())
                    .append(", Інгредієнти: ").append(p.getIngredients())
                    .append("\n");
        }
        sb.append("Відповідь українською, будь ласка.");
        return sb.toString();
    }


    private String selectBestDrinkName(String prompt) {
        while (true) {
            // Отримати три варіанти
            ChatRequest req1 = new ChatRequest(model, prompt);
            ChatRequest req2 = new ChatRequest(model2, prompt);
            ChatRequest req3 = new ChatRequest(model3, prompt);

            ChatResponse res1 = restTemplate.postForObject(apiUrl, req1, ChatResponse.class);
            ChatResponse res2 = restTemplate.postForObject(apiUrl, req2, ChatResponse.class);
            ChatResponse res3 = restTemplate.postForObject(apiUrl, req3, ChatResponse.class);

            String r1 = getReplyOrDefault(res1);
            String r2 = getReplyOrDefault(res2);
            String r3 = getReplyOrDefault(res3);

            // Вибір кращої відповіді
            String evalPrompt = String.format("""
                    Є три варіанти:
                    1: %s
                    2: %s
                    3: %s
                    Оберіть кращу відповідь. У результаті має бути лише назва напою (1-2 слова) без жодного пояснення. Назву напою напиши на англійській
                    """, r1, r2, r3);

            ChatRequest judgeRequest = new ChatRequest(model2, evalPrompt);
            ChatResponse judgeResponse = restTemplate.postForObject(apiUrl, judgeRequest, ChatResponse.class);
            String decision = getReplyOrDefault(judgeResponse);

            // Перевірка формату
            String validationPrompt = String.format("""
                    Чи є цей текст "%s" лише назвою алкогольного напою (без опису, без речень)? 
                    Відповідай лише 'yes' або 'no'.
                    """, decision);

            ChatRequest validationRequest = new ChatRequest(model2, validationPrompt);
            ChatResponse validationResponse = restTemplate.postForObject(apiUrl, validationRequest, ChatResponse.class);
            String validationResult = getReplyOrDefault(validationResponse).trim().toLowerCase();

            if (validationResult.contains("yes")) {
                return decision.trim();
            }
        }
    }

    private String getReplyOrDefault(ChatResponse response) {
        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
            return response.getChoices().get(0).getMessage().getContent();
        }
        return "No response from OpenAI.";
    }
}
