package com.kapsulesolution.llm.provider;

import com.kapsulesolution.llm.dto.LLMResponse;
import com.kapsulesolution.llm.dto.ResolvedLLMRequest;

public interface LLMProvider {
	
	String getDefaultModel();
	
	String getName();
	
	LLMResponse call(ResolvedLLMRequest paramLLMRequest);
}