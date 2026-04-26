package com.kapsulesolution.llm.provider;

import com.kapsulesolution.llm.dto.LLMResponse;
import com.kapsulesolution.llm.dto.ResolvedLLMRequest;

public interface LLMProvider {
  LLMResponse call(ResolvedLLMRequest paramLLMRequest);
}