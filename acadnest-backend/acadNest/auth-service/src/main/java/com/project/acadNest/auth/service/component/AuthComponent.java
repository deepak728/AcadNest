package com.project.acadNest.auth.service.component;


import com.project.acadNest.auth.service.builder.AuthBuilder;
import com.project.acadNest.auth.service.constant.ROLE;
import com.project.acadNest.auth.service.pojo.AuthResponsePojo;
import com.project.acadNest.auth.service.pojo.User;
import com.project.acadNest.auth.service.util.JwtUtil;
import com.project.acadNest.auth.service.util.PeopleServiceUtil;
import com.project.acadNest.people.service.pojo.Student;
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

    private final JwtUtil jwtUtil;

    private final PeopleServiceUtil peopleServiceUtil;

    private final PasswordEncoder passwordEncoder;



    public AuthResponsePojo registerUser(Map<String,Object> params) throws BadRequestException {
        validatePayload(params);

        String email = params.get("emailId").toString();
        String encodedPassword = passwordEncoder.encode(params.get("password").toString());
        ROLE role = ROLE.STUDENT;

        Student student = peopleServiceUtil.findStudentByEmail(email);

        if(student==null){
            log.error("Email doesn't exist {}",email);
            throw new BadRequestException("Email not registered");
        }

        User user = new User(null,email, encodedPassword, null, role,
                null, LocalDateTime.now(),LocalDateTime.now());
        log.info("Creating user {}",user.toString());


        try{
            authBuilder.registerUserByEmailPass(user);
        } catch (DataIntegrityViolationException e){
            if (e.getRootCause() != null && e.getRootCause().getMessage().contains("duplicate key value violates unique constraint")) {
                throw new BadRequestException("User already exists");
            }
        }

        // Generate JWT token
        String jwtToken = jwtUtil.getJwtToken(student);
        log.info("Generated token sending back : {}", jwtToken);

        return new AuthResponsePojo(jwtToken,student,true,"Success");

    }

    public AuthResponsePojo loginUser(Map<String,Object> params) throws BadRequestException{
        validatePayload(params);
        String email = params.get("emailId").toString();
        String password = params.get("password").toString();

        User user = authBuilder.findByEmail(email);
        if(user==null){
            log.error("User doesn't exist {}",email);
            throw new BadRequestException("User doesn't exist");
        }

        if(!passwordEncoder.matches(password,user.getPassword())){
            log.error("Incorrect password for given user {}",email);
            throw new BadRequestException("Incorrect password");
        }

        Student student = peopleServiceUtil.findStudentByEmail(email);

        if(student==null){
            log.error("Email doesn't exist {}",email);
            throw new BadRequestException("Email not registered");
        }

        String jwtToken = jwtUtil.getJwtToken(student);
        log.info("Generated token sending back : {}", jwtToken);

        return new AuthResponsePojo(jwtToken,student,true,"Success");
    }

    private void validatePayload(Map<String,Object> params) throws BadRequestException {
        if(params == null || !params.containsKey("emailId") || !params.containsKey("password"))
            throw new BadRequestException("Invalid Payload");

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String emailId = params.get("emailId").toString();
        String password = params.get("password").toString();


        if(emailId.isEmpty() || !emailId.matches(emailRegex))
            throw new BadRequestException("Invalid Email");

        if(password.isEmpty())
            throw new BadRequestException("Invalid Password");
    }
}
