package com.kapsulesolution.llm.service;

import com.kapsulesolution.llm.dto.LLMRequest;
import com.kapsulesolution.llm.dto.LLMResponse;

public interface LLMService {
  LLMResponse interpret(LLMRequest paramLLMRequest);
}
