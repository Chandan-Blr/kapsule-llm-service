package com.kapsulesolution.llm.controller;

import com.kapsulesolution.llm.dto.ApiResponse;
import com.kapsulesolution.llm.dto.LLMRequest;
import com.kapsulesolution.llm.dto.LLMResponse;
import com.kapsulesolution.llm.service.LLMService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/llm")
public class LLMController {

    private final LLMService llmService;

    public LLMController(LLMService llmService) {
        this.llmService = llmService;
    }

    @PostMapping("/interpret")
    public ResponseEntity<ApiResponse<LLMResponse>> interpret(@RequestBody LLMRequest request) {

        LLMResponse response = llmService.interpret(request);

        return ResponseEntity.ok(
                ApiResponse.success("LLM interpretation successful", response)
        );
    }

    @PostMapping("/chat")
    public ResponseEntity<ApiResponse<LLMResponse>> chat(@RequestBody LLMRequest request) {

        LLMResponse response = llmService.interpret(request);

        return ResponseEntity.ok(
                ApiResponse.success("LLM response generated", response)
        );
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Object>> health() {

        return ResponseEntity.ok(
                ApiResponse.success("LLM service is healthy", null)
        );
    }
}