package com.kapsulesolution.llm.provider;

import com.kapsulesolution.llm.dto.LLMRequest;
import com.kapsulesolution.llm.dto.LLMResponse;

public interface LLMProvider {
  LLMResponse call(LLMRequest paramLLMRequest);
}