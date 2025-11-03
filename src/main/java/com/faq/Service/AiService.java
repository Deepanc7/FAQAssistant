package com.faq.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class AiService {

    private final WebClient webClient;

    public AiService(@Value("${openrouter.api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://openrouter.ai/api/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String generateAnswer(String question) {
        Map<String, Object> request = Map.of(
                "model", "meta-llama/llama-4-scout:free",
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a helpful assistant that answers FAQ questions."),
                        Map.of("role", "user", "content", question)
                )
        );

        try {
            Map<String, Object> response = webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response == null || !response.containsKey("choices")) {
                return "No response from model.";
            }

            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (choices.isEmpty()) return "No choices in response.";

            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            if (message == null || !message.containsKey("content")) {
                return "No message content found.";
            }

            return message.get("content").toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error calling model: " + e.getMessage();
        }
    }
}
