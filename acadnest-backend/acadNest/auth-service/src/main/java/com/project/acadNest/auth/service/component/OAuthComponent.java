package com.project.acadNest.auth.service.component;

import com.project.acadNest.auth.service.builder.OAuthBuilder;
import com.project.acadNest.auth.service.client.PeopleServiceClient;
import com.project.acadNest.auth.service.constant.ROLE;
import com.project.acadNest.auth.service.pojo.AuthResponsePojo;
import com.project.acadNest.auth.service.pojo.User;
import com.project.acadNest.auth.service.util.JwtUtil;
import com.project.acadNest.people.service.builder.StudentBuilder;
import com.project.acadNest.people.service.pojo.Student;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OAuthComponent {

    private final JwtUtil jwtUtil;
    private final OAuthBuilder oAuthBuilder;

    private final StudentBuilder studentBuilder;

    private final PeopleServiceClient peopleServiceClient;


    public AuthResponsePojo createUser(String email,String googleId) throws BadRequestException {

        Student student = null;

        try{
            student = peopleServiceClient.getStudentByEmail(email);
        } catch (BadRequestException e){
            log.error("exception occurred while fetching student by email {}, {}",email,e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (Exception e){
            log.error("exception occurred while fetching student by email {},{}",email,e.getMessage());
            throw new BadRequestException("Server error");
        }

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
        String jwtToken = jwtUtil.generateToken(Map.of(
                "email", student.getEmailId(),
                "name", student.getName(),
                "rollNo", student.getRollNo(),
                "branch", student.getBranch().toString(),
                "year",student.getYear().toString(),
                "phone", student.getPhoneNo(),
                "photo", student.getPhoto()
        ));

        log.info("Generated token sending back : {}", jwtToken);

        return new AuthResponsePojo(jwtToken,student,true,"Success");
    }


}
