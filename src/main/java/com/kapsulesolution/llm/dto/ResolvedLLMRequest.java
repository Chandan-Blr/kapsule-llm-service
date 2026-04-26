package com.kapsulesolution.llm.dto;

import java.util.List;

public class ResolvedLLMRequest {

    private String systemPrompt;
    private String userPrompt;
    private List<ChatMessage> history;
    private Double temperature;
    private String provider;
    private String model;
    
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

	public void setSystemPrompt(String systemPrompt) {
		this.systemPrompt = systemPrompt;
	}

	public String getUserPrompt() {
		return userPrompt;
	}

	public void setUserPrompt(String userPrompt) {
		this.userPrompt = userPrompt;
	}

	public List<ChatMessage> getHistory() {
		return history;
	}

	public void setHistory(List<ChatMessage> history) {
		this.history = history;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
    
    
}
