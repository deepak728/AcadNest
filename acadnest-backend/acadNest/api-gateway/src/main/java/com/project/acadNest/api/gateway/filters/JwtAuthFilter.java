package com.project.acadNest.api.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@Order(Ordered.LOWEST_PRECEDENCE)  // Ensures it runs after route resolution
public class JwtAuthFilter implements GlobalFilter {

    @Value("${auth.login-page}")
    private String loginPageUrl;

    private static final List<String> INTERNAL_IP_PREFIXES = List.of("10.", "192.168.", "172.16.");
    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/auth/login",
            "/auth/signup",
            "/oauth2/authorization/google"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        log.info("Received req {}", request.getURI());
        log.info("Received header {}", request.getHeaders());

        // Allow internal requests
        if (isInternalRequest(request)) {
            return chain.filter(exchange);
        }

        // Allow public endpoints without JWT
        if (isPublicEndpoint(path)) {
            return chain.filter(exchange);
        }

        // Check for Authorization Header
        if (!request.getHeaders().containsKey("Authorization")) {
            return redirectToLogin(exchange.getResponse());
        }

        String authHeader = request.getHeaders().getFirst("Authorization");

        // 🔥 Reject requests with "Bearer null" or invalid tokens
        if (isInvalidToken(authHeader)) {
            log.warn("Unauthorized request with invalid token: {}", authHeader);
            return rejectUnauthorized(exchange.getResponse());
        }

        return chain.filter(exchange);
    }

    private boolean isInternalRequest(ServerHttpRequest request) {
        return false;
//        String ipAddress = request.getRemoteAddress().getAddress().getHostAddress();
//        log.info("Incoming request from IP: " + ipAddress);
//        return "127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress) ||
//                INTERNAL_IP_PREFIXES.stream().anyMatch(ipAddress::startsWith);
    }

    private boolean isPublicEndpoint(String path) {
        return PUBLIC_ENDPOINTS.stream().anyMatch(path::startsWith);
    }

    private boolean isInvalidToken(String authHeader) {
        return authHeader == null || authHeader.equalsIgnoreCase("Bearer null") || !authHeader.startsWith("Bearer ");
    }

    private Mono<Void> redirectToLogin(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);  // Return 401 instead of redirecting
        return response.setComplete();
    }

    private Mono<Void> rejectUnauthorized(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
