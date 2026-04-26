package com.kapsulesolution.llm.service.impl;

import com.kapsulesolution.llm.config.LLMProperties;
import com.kapsulesolution.llm.dto.LLMRequest;
import com.kapsulesolution.llm.dto.LLMResponse;
import com.kapsulesolution.llm.dto.ResolvedLLMRequest;
import com.kapsulesolution.llm.provider.LLMProvider;
import com.kapsulesolution.llm.provider.ProviderFactory;
import com.kapsulesolution.llm.service.LLMService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LLMServiceImpl implements LLMService {

    private final ProviderFactory factory;
    private final LLMProperties props;

    public LLMServiceImpl(ProviderFactory factory, LLMProperties props) {
        this.factory = factory;
        this.props = props;
    }

    @Override
    public LLMResponse interpret(LLMRequest request) {

        // Step 1: resolve provider first
        String providerName = (request.getProvider() != null && !request.getProvider().isBlank())
                ? request.getProvider()
                : props.getDefaultProvider();

        LLMProvider provider = factory.getProvider(providerName);

        // Step 2: resolve final request using provider defaults
        ResolvedLLMRequest resolved = resolve(request, provider);

        return provider.call(resolved);
    }

    private ResolvedLLMRequest resolve(LLMRequest req, LLMProvider provider) {

        if (req == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        if (req.getUserPrompt() == null || req.getUserPrompt().isBlank()) {
            throw new IllegalArgumentException("userPrompt is required");
        }

        String model = (req.getModel() != null && !req.getModel().isBlank())
                ? req.getModel()
                : provider.getDefaultModel(); // KEY CHANGE

        return new ResolvedLLMRequest(

                // systemPrompt
                req.getSystemPrompt() != null
                        ? req.getSystemPrompt()
                        : "You are a helpful assistant",

                // userPrompt
                req.getUserPrompt(),

                // history
                req.getHistory() != null
                        ? req.getHistory()
                        : new ArrayList<>(),

                // temperature
                req.getTemperature() != null
                        ? req.getTemperature()
                        : props.getDefaultTemperature(),

                // provider
                provider.getName(),

                // model
                model
        );
    }
}