package com.project.acadNest.auth.service.util;


import com.project.acadNest.auth.service.client.PeopleServiceClient;
import com.project.acadNest.people.service.pojo.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PeopleServiceUtil {

    private final PeopleServiceClient peopleServiceClient;


    public Student findStudentByEmail(String email) throws BadRequestException {
        Student student =null;
        try{
            student = peopleServiceClient.getStudentByEmail(email);
        } catch (BadRequestException e){
            log.error("exception occurred while fetching student by email {}, {}",email,e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (Exception e){
            log.error("exception occurred while fetching student by email {},{}",email,e.getMessage());
            throw new BadRequestException("Server error");
        }
        return student;
    }
}
