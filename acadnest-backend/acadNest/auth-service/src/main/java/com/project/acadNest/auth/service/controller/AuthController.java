package com.project.acadNest.auth.service.controller;


import com.project.acadNest.auth.service.component.AuthComponent;
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

    @PostMapping("user/register")
    public ResponseEntity<?> registerUser(@RequestBody @NonNull Map<String,Object> request) {
        log.debug("Received request to register user with payload {}",request);
        try{

            String response = authComponent.registerUser(request);
            return ResponseEntity.ok(response);
        } catch (BadRequestException e){
            log.error("Exception occurred while registering user {}",e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            log.error("Exception occurred while registering user {}",e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
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
