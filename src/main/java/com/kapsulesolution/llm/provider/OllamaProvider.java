package com.kapsulesolution.llm.provider;

import com.kapsulesolution.llm.config.LLMProperties;
import com.kapsulesolution.llm.dto.ChatMessage;
import com.kapsulesolution.llm.dto.LLMResponse;
import com.kapsulesolution.llm.dto.ResolvedLLMRequest;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service("ollama")
public class OllamaProvider implements LLMProvider {

    private final RestTemplate restTemplate = new RestTemplate();

    // You can also move this to LLMProperties
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    
    @Override
    public String getName() {
        return "ollama";
    }

    @Override
    public String getDefaultModel() {
        return "llama3";
    }

    @Override
    public LLMResponse call(ResolvedLLMRequest request) {

        // 🔹 Build prompt (IMPORTANT: Ollama is prompt-based, not messages-based)
        StringBuilder prompt = new StringBuilder();

        // system prompt
        if (request.getSystemPrompt() != null) {
            prompt.append("System: ").append(request.getSystemPrompt()).append("\n\n");
        }

        // history
        if (request.getHistory() != null) {
            for (ChatMessage msg : request.getHistory()) {
                prompt.append(msg.getRole())
                      .append(": ")
                      .append(msg.getContent())
                      .append("\n");
            }
        }

        // user
        prompt.append("User: ").append(request.getUserPrompt());

        System.out.println("Ollama Prompt:\n" + prompt);

        // 🔹 Request body
        Map<String, Object> body = new HashMap<>();
        body.put("model", request.getModel()); // e.g. llama3
        body.put("prompt", prompt.toString());
        body.put("stream", false); // IMPORTANT (else streaming JSON)

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(OLLAMA_URL, entity, Map.class);

        Map responseBody = response.getBody();

        // 🔹 Extract response
        String content = (String) responseBody.get("response");

        LLMResponse res = new LLMResponse();
        res.setMessage(content);
        res.setConfidence(0.9);

        return res;
    }
}