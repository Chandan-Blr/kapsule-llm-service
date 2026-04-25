package com.kapsulesolution.llm.service.impl;

import com.kapsulesolution.llm.dto.LLMRequest;
import com.kapsulesolution.llm.dto.LLMResponse;
import com.kapsulesolution.llm.provider.LLMProvider;
import com.kapsulesolution.llm.provider.ProviderFactory;
import com.kapsulesolution.llm.service.LLMService;

import org.springframework.stereotype.Service;

@Service
public class LLMServiceImpl implements LLMService {

    private final ProviderFactory factory;

    public LLMServiceImpl(ProviderFactory factory) {
        this.factory = factory;
    }

    @Override
    public LLMResponse interpret(LLMRequest request) {

        LLMProvider provider = factory.getProvider(request.getProvider());

        // defensive check (factory already throws if not found)
        if (provider == null) {
            throw new RuntimeException("Invalid LLM provider");
        }

        return provider.call(request);
    }
}