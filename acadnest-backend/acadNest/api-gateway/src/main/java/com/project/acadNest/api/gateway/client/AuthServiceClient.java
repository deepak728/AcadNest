package com.project.acadNest.api.gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "http://localhost:8081")
public interface AuthServiceClient {

    @GetMapping("auth/validate/token")
    ResponseEntity<String> validateToken(@RequestParam("token") String token);
}