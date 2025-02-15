package com.project.acadNest.api.gateway.filters;

import com.project.acadNest.api.gateway.client.AuthServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAuthFilter implements WebFilter {

    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/auth/login",
            "/auth/signup",
            "/oauth2/authorization/google",
            "/api-gateway/oauth2/authorization/google"
    );
    private final AuthServiceClient authServiceClient;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        log.info("received request {} {}",request.getURI(),request.getHeaders());

        if (exchange.getRequest().getMethod().equals(HttpMethod.OPTIONS)) {
            return chain.filter(exchange); // Allow OPTIONS requests
        }

        if (PUBLIC_ENDPOINTS.stream().anyMatch(path::startsWith)) {
            log.info("Skipping validation. Public api {}",request.getURI());
            return chain.filter(exchange);
        }

        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            log.error("Doesn't have header AUTHORIZATION ");
            return handleUnauthorized(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("Invalid header AUTHORIZATION");
            return handleUnauthorized(exchange);
        }

        String token = authHeader.substring(7);

        ResponseEntity<String> response = authServiceClient.validateToken(token);

        if (response.getBody().toString().equals("valid")) {
            log.info("Token is valid");
            return chain.filter(exchange);
        } else {
            log.error("Invalid token");
            return handleUnauthorized(exchange);
        }

    }

    private Mono<Void> handleUnauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
