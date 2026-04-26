package com.kapsulesolution.llm.provider;

import com.kapsulesolution.llm.config.LLMProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProviderFactory {

    private final Map<String, LLMProvider> providers;
    private final LLMProperties props;

    public ProviderFactory(Map<String, LLMProvider> providers, LLMProperties props) {
        this.providers = providers;
        this.props = props;
    }

    public LLMProvider getProvider(String name) {

        String providerName = (name != null && !name.isBlank())
                ? name.toLowerCase()
                : props.getDefaultProvider().toLowerCase();

        LLMProvider provider = providers.get(providerName);

        if (provider == null) {
            throw new RuntimeException(
                "LLM Provider not found: " + providerName +
                ". Available: " + providers.keySet()
            );
        }

        return provider;
    }
}