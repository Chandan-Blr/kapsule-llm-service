package com.kapsulesolution.llm.provider;

import com.kapsulesolution.llm.config.LLMProperties;
import com.kapsulesolution.llm.dto.ChatMessage;
import com.kapsulesolution.llm.dto.LLMRequest;
import com.kapsulesolution.llm.dto.LLMResponse;
import com.kapsulesolution.llm.dto.ResolvedLLMRequest;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service("openai")
public class OpenAIProvider implements LLMProvider {

    private final LLMProperties props;
    private final RestTemplate restTemplate = new RestTemplate();

    public OpenAIProvider(LLMProperties props) {
        this.props = props;
    }
    
    @Override
    public String getName() {
        return "openai";
    }

    @Override
    public String getDefaultModel() {
        return "gpt-4o-mini";
    }

    @Override
    public LLMResponse call(ResolvedLLMRequest request) {

        String url = props.getOpenaiUrl();

        Map<String, Object> body = new HashMap<>();
     
        // model prompt
        if (request.getModel() != null) {
        	 body.put("model",request.getModel());
           
        }

        List<Map<String, String>> messages = new ArrayList<>();

        // system prompt
        if (request.getSystemPrompt() != null) {
            messages.add(Map.of(
                    "role", "system",
                    "content", request.getSystemPrompt()
            ));
        }
      
        // history
        if (request.getHistory() != null) {
            for (ChatMessage msg : request.getHistory()) {
                messages.add(Map.of(
                        "role", msg.getRole(),
                        "content", msg.getContent()
                ));
            }
        }

        // user prompt
        messages.add(Map.of(
                "role", "user",
                "content", request.getUserPrompt()
        ));
;
        System.out.println("User prompt:" +  request.getUserPrompt());
        
        body.put("messages", messages);

        // temperature fallback
        Double temperature = request.getTemperature() != null
                ? request.getTemperature()
                : props.getDefaultTemperature();

        body.put("temperature", temperature);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(props.getOpenaiApiKey());
        System.out.print("Open AI APi Key:" + props.getOpenaiApiKey());
        
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, entity, Map.class);

        // extract response
        Map responseBody = response.getBody();

        String content = (String) ((Map) ((Map)
                ((List) responseBody.get("choices")).get(0))
                .get("message"))
                .get("content");

        LLMResponse res = new LLMResponse();
        res.setMessage(content);
        res.setConfidence(0.9);

        return res;
    }
}