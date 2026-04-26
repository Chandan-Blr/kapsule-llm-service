package com.kapsulesolution.llm.dto.internal;

import java.util.List;

import com.kapsulesolution.llm.dto.ChatMessage;

public class ResolvedLLMRequest {

    private final String systemPrompt;
    private final String userPrompt;
    private final List<ChatMessage> history;
    private final Double temperature;
    private final String provider;
    private final String model;

    public ResolvedLLMRequest(
            String systemPrompt,
            String userPrompt,
            List<ChatMessage> history,
            Double temperature,
            String provider,
            String model) {

        this.systemPrompt = systemPrompt;
        this.userPrompt = userPrompt;
        this.history = history;
        this.temperature = temperature;
        this.provider = provider;
        this.model = model;
    }

	public String getSystemPrompt() {
		return systemPrompt;
	}

	public String getUserPrompt() {
		return userPrompt;
	}

	public List<ChatMessage> getHistory() {
		return history;
	}

	public Double getTemperature() {
		return temperature;
	}

	public String getProvider() {
		return provider;
	}

	public String getModel() {
		return model;
	}
    
}