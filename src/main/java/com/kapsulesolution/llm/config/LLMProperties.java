package com.kapsulesolution.llm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "llm")
public class LLMProperties {

    private String defaultProvider = "openai";
    private Double defaultTemperature = 0.7;
    private String openaiApiKey;
    private String openaiUrl = "https://api.openai.com/v1/chat/completions";

    public String getDefaultProvider() {
        return defaultProvider;
    }

    public void setDefaultProvider(String defaultProvider) {
        this.defaultProvider = defaultProvider;
    }

    public Double getDefaultTemperature() {
        return defaultTemperature;
    }

    public void setDefaultTemperature(Double defaultTemperature) {
        this.defaultTemperature = defaultTemperature;
    }

    public String getOpenaiApiKey() {
        return openaiApiKey;
    }

    public void setOpenaiApiKey(String openaiApiKey) {
        this.openaiApiKey = openaiApiKey;
    }

    public String getOpenaiUrl() {
        return openaiUrl;
    }

    public void setOpenaiUrl(String openaiUrl) {
        this.openaiUrl = openaiUrl;
    }
}