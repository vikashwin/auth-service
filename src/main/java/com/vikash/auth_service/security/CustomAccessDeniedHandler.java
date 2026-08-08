package com.vikash.auth_service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

//        HandlerExceptionResolver.resolveException(request, response , null , AccessDeniedException); It helps to handle exception by Global Exception handler

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", 403);
        body.put("error", "Forbidden");
        body.put("message", "You don't have permission to access this resource.");
        body.put("path", request.getRequestURI());

        objectMapper.writeValue(response.getOutputStream(), body);

    }
}
