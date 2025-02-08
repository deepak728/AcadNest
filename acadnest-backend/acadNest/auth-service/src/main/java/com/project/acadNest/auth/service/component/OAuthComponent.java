package com.project.acadNest.auth.service.component;

import com.project.acadNest.auth.service.builder.OAuthBuilder;
import com.project.acadNest.auth.service.constant.ROLE;
import com.project.acadNest.auth.service.pojo.AuthResponsePojo;
import com.project.acadNest.auth.service.pojo.User;
import com.project.acadNest.auth.service.util.JwtUtil;
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


    public AuthResponsePojo createUser(String email,String googleId) throws BadRequestException {
//        Student student =  fetchStudentProfile(email);
//        if(student==null){
//            log.error("Student profile doesn't exist");
//            throw new BadRequestException("Student doesn't exist with email "+email);
//        }
//        log.info("Fetched student profile {}",student);

        User user = new User();
        user.setGoogleId(googleId);
        user.setEmailId(email);
        user.setRole(ROLE.STUDENT);

        user = oAuthBuilder.saveUser(user);
        log.info("User details: {}", user);

        // Generate JWT token
        String jwtToken = jwtUtil.generateToken(email, ROLE.STUDENT.toString());
        log.info("Generated token: {}", jwtToken);

        return new AuthResponsePojo(jwtToken,true,user.toString());
    }

//    Student fetchStudentProfile(String email){
//        Student student = new Student();
//        student.setId(101);
//        student.setEmailId(email);
//        student.setName("Deepak");
//        student.setBranch(Branch.ELECTRICAL);
//        student.setRollNo(15115043L);
//        student.setYear(Year.FOURTH);
//        student.setPhoneNo("12345678");
//        student.setPhoto("https://i.pinimg.com/1200x/79/e1/c2/79e1c247e1b703aed98a013e87c1ac79.jpg");
//        return student;
//    }

}
