package com.project.acadNest.auth.service.client;

import com.project.acadNest.people.service.pojo.Student;
import org.apache.coyote.BadRequestException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "people-service", url = "http://localhost:8080")
public interface PeopleServiceClient {

    @GetMapping("/people/student/getByEmail")
    Student getStudentByEmail(@RequestParam("email") String email) throws BadRequestException;
}
