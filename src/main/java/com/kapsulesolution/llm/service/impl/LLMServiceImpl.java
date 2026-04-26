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

        // ✅ Resolve request (IMPORTANT)
        ResolvedLLMRequest resolved = resolve(request);

        // ✅ Use resolved provider
        LLMProvider provider = factory.getProvider(resolved.getProvider());

        if (provider == null) {
            throw new RuntimeException("Invalid LLM provider: " + resolved.getProvider());
        }

        // ✅ Call provider with resolved request
        return provider.call(resolved);
    }

    private ResolvedLLMRequest resolve(LLMRequest req) {

        if (req == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        if (req.getUserPrompt() == null || req.getUserPrompt().isBlank()) {
            throw new IllegalArgumentException("userPrompt is required");
        }

        return new ResolvedLLMRequest(

                // systemPrompt
                req.getSystemPrompt() != null
                        ? req.getSystemPrompt()
                        : "You are a helpful assistant",

                // userPrompt (mandatory)
                req.getUserPrompt(),

                // history
                req.getHistory() != null
                        ? req.getHistory()
                        : new ArrayList<>(),

                // temperature (FIXED TYPE)
                req.getTemperature() != null
                        ? req.getTemperature()
                        : props.getDefaultTemperature(),

                // provider
                (req.getProvider() != null && !req.getProvider().isBlank())
                        ? req.getProvider()
                        : props.getDefaultProvider(),

                // model
                (req.getModel() != null && !req.getModel().isBlank())
                        ? req.getModel()
                        : "gpt-4o-mini"
        );
    }
}