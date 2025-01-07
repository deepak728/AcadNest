package com.project.acadNest.people.service.controller;


import com.project.acadNest.people.service.component.StudentComponent;
import com.project.acadNest.people.service.pojo.Student;
import com.project.acadNest.people.service.pojo.StudentResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        log.debug("Received request to search student with payload {}",request);
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

    @PostMapping("/addStudent")
    public ResponseEntity<?> addStudent(@RequestBody @NonNull Map<String,Object> request){
        log.debug("Received request to add student with payload {}",request);
        try{

            Student student = studentComponent.addStudent(request);
            return ResponseEntity.ok(student);
        } catch (BadRequestException e){
            log.error("Exception occurred while adding student {}",e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            log.error("Exception occurred while adding student {}",e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        log.debug("Received request to delete student with ID: {}", id);
        try {
            studentComponent.deleteStudentById(id);
            return ResponseEntity.ok("Success");
        } catch (BadRequestException e) {
            log.error("Exception occurred while deleting student: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Exception occurred while deleting student: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
