package com.project.acadNest.auth.service.component;

import com.project.acadNest.auth.service.builder.OAuthBuilder;
import com.project.acadNest.auth.service.client.PeopleServiceClient;
import com.project.acadNest.auth.service.constant.ROLE;
import com.project.acadNest.auth.service.pojo.AuthResponsePojo;
import com.project.acadNest.auth.service.pojo.User;
import com.project.acadNest.auth.service.util.JwtUtil;
import com.project.acadNest.auth.service.util.PeopleServiceUtil;
import com.project.acadNest.people.service.builder.StudentBuilder;
import com.project.acadNest.people.service.pojo.Student;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OAuthComponent {

    private final JwtUtil jwtUtil;
    private final OAuthBuilder oAuthBuilder;

    private final StudentBuilder studentBuilder;

    private final PeopleServiceClient peopleServiceClient;

    private final PeopleServiceUtil peopleServiceUtil;


    public AuthResponsePojo createUser(String email,String googleId) throws BadRequestException {

        Student student = peopleServiceUtil.findStudentByEmail(email);

        if(student==null){
            log.error("Email doesn't exist {}",email);
            throw new BadRequestException("Email not registered");
        }

        User user = new User();
        user.setGoogleId(googleId);
        user.setEmailId(email);
        user.setRole(ROLE.STUDENT);

        user = oAuthBuilder.saveUser(user);
        log.info("User details: {}", user);



        // Generate JWT token
        String jwtToken = jwtUtil.getJwtToken(student);

        log.info("Generated token sending back : {}", jwtToken);

        return new AuthResponsePojo(jwtToken,student,true,"Success");
    }


}
