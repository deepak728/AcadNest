package com.project.acadNest.people.service.controller;


import com.project.acadNest.people.service.component.StudentComponent;
import com.project.acadNest.people.service.pojo.Student;
import com.project.acadNest.people.service.pojo.StudentResponse;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/people/student")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController {

    private final StudentComponent studentComponent;


    @PostMapping("/search")
    public ResponseEntity<?> searchStudent(@RequestBody @NonNull Map<String,Object> request) {
        try{
            List<StudentResponse> students = studentComponent.findStudent(request);
            return ResponseEntity.ok(students);
        } catch (BadRequestException e){
            log.error("Exception occurred while searching student {}",e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            log.error("Exception occurred while searching student {}",e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
