package com.project.acadNest.auth.service.component;


import com.project.acadNest.auth.service.builder.AuthBuilder;
import com.project.acadNest.auth.service.constant.ROLE;
import com.project.acadNest.auth.service.pojo.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthComponent {

    private final AuthBuilder authBuilder;

    private final PasswordEncoder passwordEncoder;

    public String registerUser(Map<String,Object> params) throws BadRequestException {
        validatePayload(params);

        String email = params.get("emailId").toString();
        String encodedPassword = passwordEncoder.encode(params.get("password").toString());
        ROLE role = ROLE.valueOf(params.get("role").toString().trim().toUpperCase());

        User user = new User(null,email, encodedPassword, null, role,
                null, LocalDateTime.now(),LocalDateTime.now());
        log.info("Creating user {}",user.toString());


        try{
            if(authBuilder.registerUserByEmailPass(user))
                return role.toString() + " added Successfully";
        } catch (DataIntegrityViolationException e){
            if (e.getRootCause() != null && e.getRootCause().getMessage().contains("duplicate key value violates unique constraint")) {
                throw new BadRequestException("User already exists");
            }
        }

        return "Unsuccessful";
    }

    private void validatePayload(Map<String,Object> params) throws BadRequestException {
        if(params == null || !params.containsKey("emailId") || !params.containsKey("password"))
            throw new BadRequestException("Invalid Payload");

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String emailId = params.get("emailId").toString();
        String password = params.get("password").toString();

        try{
            ROLE.valueOf(params.get("role").toString().trim().toUpperCase());
        } catch (Exception e){
            throw new BadRequestException("Invalid Role");
        }

        if(emailId.isEmpty() || !emailId.matches(emailRegex))
            throw new BadRequestException("Invalid Email");

        if(password.isEmpty())
            throw new BadRequestException("Invalid Password");
    }
}
