package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.*;

public class LoadTest {

    private final String SIGNUP_URL = "http://localhost:8080/req/signup";
    private final String LOGIN_URL = "http://localhost:8080/req/login";

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testSignupLoad() throws InterruptedException {
        int userCount = 100; // Кількість одночасних запитів
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(userCount);

        for (int i = 0; i < userCount; i++) {
            int finalI = i;
            executor.submit(() -> {
                try {
                    Map<String, Object> user = new HashMap<>();
                    user.put("username", "user" + finalI);
                    user.put("email", "user" + finalI + "@test.com");
                    user.put("password", "password");

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<Map<String, Object>> request = new HttpEntity<>(user, headers);

                    ResponseEntity<String> response = restTemplate.postForEntity(SIGNUP_URL, request, String.class);
                    System.out.println("User " + finalI + " - Status: " + response.getStatusCode());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
    }
}
class LoadTest2 {

    private final String SIGNUP_URL = "http://localhost:8080/req/signup";
    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testSignupLoad() throws InterruptedException {
        int userCount = 1000; // Кількість одночасних користувачів
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(userCount);
        List<Long> responseTimes = Collections.synchronizedList(new ArrayList<>());

        long startTotal = System.currentTimeMillis(); // Початок загального тесту

        for (int i = 0; i < userCount; i++) {
            int finalI = i;
            executor.submit(() -> {
                try {
                    long start = System.currentTimeMillis();

                    Map<String, Object> user = new HashMap<>();
                    user.put("username", "user" + finalI);
                    user.put("email", "user" + finalI + "@test.com");
                    user.put("password", "password");

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<Map<String, Object>> request = new HttpEntity<>(user, headers);

                    ResponseEntity<String> response = restTemplate.postForEntity(SIGNUP_URL, request, String.class);

                    long end = System.currentTimeMillis();
                    long duration = end - start;
                    responseTimes.add(duration);

                    System.out.println("User " + finalI + " - Status: " + response.getStatusCode() + " - Time: " + duration + " ms");

                } catch (Exception e) {
                    System.err.println("Error for user " + finalI + ": " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        long endTotal = System.currentTimeMillis();
        long totalDuration = endTotal - startTotal;

        // Обробка статистики
        long max = responseTimes.stream().mapToLong(Long::longValue).max().orElse(0);
        long min = responseTimes.stream().mapToLong(Long::longValue).min().orElse(0);
        double avg = responseTimes.stream().mapToLong(Long::longValue).average().orElse(0);

        System.out.println("\n--- Load Test Summary ---");
        System.out.println("Total requests: " + userCount);
        System.out.println("Total time: " + totalDuration + " ms");
        System.out.println("Average response time: " + avg + " ms");
        System.out.println("Min response time: " + min + " ms");
        System.out.println("Max response time: " + max + " ms");
    }
}
