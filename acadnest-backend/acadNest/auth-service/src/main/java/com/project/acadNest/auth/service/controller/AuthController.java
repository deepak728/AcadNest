package com.project.acadNest.auth.service.controller;


import com.project.acadNest.auth.service.component.AuthComponent;
import com.project.acadNest.auth.service.pojo.AuthResponsePojo;
import com.project.acadNest.auth.service.util.JwtUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthComponent authComponent;
    private final JwtUtil jwtUtil;

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody @NonNull Map<String, Object> request) {
        log.debug("Received request to register user with payload {}", request);

        try {
            AuthResponsePojo authResponsePojo = authComponent.registerUser(request);
            String jwt = authResponsePojo.getJwt();

            return ResponseEntity.ok(Map.of("jwt", jwt));
        } catch (BadRequestException e) {
            log.error("Exception during registration: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Internal error during registration: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal error"));
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@RequestBody @NonNull Map<String, Object> request) {
        log.debug("Received request to login user with payload {}", request);

        try {
            AuthResponsePojo authResponsePojo = authComponent.loginUser(request);
            String jwt = authResponsePojo.getJwt();

            return ResponseEntity.ok(Map.of("jwt", jwt));
        } catch (BadRequestException e) {
            log.error("Exception during login: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Internal error during login: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal error"));
        }
    }

    @GetMapping("/validate/token")
    public ResponseEntity<?> validateToken(@RequestParam("token") String token) {
        log.info("Received request to validate token: {}", token);

        if (jwtUtil.validateToken(token)) {
            log.info("valid token {}",token);
            return new ResponseEntity<>("valid",HttpStatus.OK);
        }
        log.info("invalid token {}",token);
        return new ResponseEntity<>("invalid",HttpStatus.OK);
    }

}
