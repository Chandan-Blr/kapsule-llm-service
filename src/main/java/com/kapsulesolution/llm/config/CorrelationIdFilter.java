package com.kapsulesolution.llm.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class CorrelationIdFilter implements Filter {

    private static final String HEADER = "X-Correlation-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String correlationId = req.getHeader(HEADER);

        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
        }

        try {
            MDC.put("correlationId", correlationId);

            res.setHeader(HEADER, correlationId);

            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}