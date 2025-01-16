package com.project.acadNest.auth.service.controller;


import com.project.acadNest.auth.service.component.AuthComponent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthComponent authComponent;

    @PostMapping("/register")
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
}
