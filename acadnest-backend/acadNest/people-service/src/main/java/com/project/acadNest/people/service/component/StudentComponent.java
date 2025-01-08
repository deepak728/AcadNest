package com.project.acadNest.people.service.component;


import com.project.acadNest.people.service.builder.StudentBuilder;
import com.project.acadNest.people.service.constant.Branch;
import com.project.acadNest.people.service.constant.Year;
import com.project.acadNest.people.service.pojo.Student;
import com.project.acadNest.people.service.pojo.StudentResponse;
import com.project.acadNest.people.service.transformer.StudentTransformer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.project.acadNest.people.service.constant.Constants.*;

@Data
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class StudentComponent {

    private final StudentBuilder studentBuilder;

    public List<StudentResponse> findStudent(Map<String,Object> params) throws BadRequestException {

        if(params==null || params.size()==0 || !params.containsKey(FIELD))
            throw new BadRequestException("Invalid payload");
        List<Student> studentList = null;
        String field = params.get(FIELD).toString().trim();

        switch (findInputType(field)) {
            case NAME:
                studentList = studentBuilder.findStudentByNameRegex(field);
                break;
            case EMAIL_Id:
                studentList = studentBuilder.findStudentByEmailRegex(field);
                break;
            case ROLL_NO:
                studentList = studentBuilder.findStudentByRollNoRegex(field);
                break;
            default:
                log.error("Invalid Input {}", params);
        }

        log.info("fetched students list :{}",studentList.toString());
        return StudentTransformer.toStudentResponseList(studentList);
    }

    private String findInputType(String field){
        if(field == null || field.isEmpty()) return INVALID;

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String rollNoRegex = "^\\d+$";
        String nameRegex = "^[A-Za-z\\s.'-]+$";

        if (field.matches(emailRegex)) {
            return EMAIL_Id;
        } else if (field.matches(rollNoRegex)) {
            return ROLL_NO;
        } else if (field.matches(nameRegex)) {
            return NAME;
        } else {
            return INVALID;
        }
    }

    public Student addStudent(Map<String,Object> params) throws BadRequestException {
        validateStudent(params);

        Student savedStudent= null;

        try{
            savedStudent= studentBuilder.save(getStudentPojo(params));
            log.info("Saved Student {}",savedStudent);
        } catch (DataIntegrityViolationException e){
            if (e.getRootCause() != null && e.getRootCause().getMessage().contains("duplicate key value violates unique constraint")) {
                throw new BadRequestException("Roll number already exists");
            }
        }

        if(savedStudent==null)
            throw new BadRequestException("Unsuccessful");
        return savedStudent;
    }

    private Student getStudentPojo(Map<String, Object> params) {
        try {
            Student student = new Student();

            // Trim all string fields
            student.setName(params.get(NAME).toString().trim());
            student.setRollNo(Long.parseLong(params.get(ROLL_NO).toString().trim()));
            student.setEmailId(params.containsKey(EMAIL_Id) ? params.get(EMAIL_Id).toString().trim() : "");
            student.setBranch(Branch.valueOf(params.get(BRANCH).toString().trim().toUpperCase()));
            student.setYear(Year.valueOf(params.get(YEAR).toString().trim().toUpperCase()));
            student.setPhoto(params.containsKey(PHOTO) ? params.get(PHOTO).toString().trim() : "");
            student.setPhoneNo(params.containsKey(PHONE_NO) ? params.get(PHONE_NO).toString().trim() : "");

            return student;
        } catch (Exception e) {
            log.error("Error creating Student POJO: {}", e.getMessage());
            return null;
        }
    }

    private void validateStudent(Map<String, Object> params) throws BadRequestException {
        if(params==null || params.size()==0 ) throw new BadRequestException("Invalid payload");

        if(!params.containsKey(NAME) || !findInputType(params.get(NAME).toString()).equals(NAME))
            throw new BadRequestException("Invalid name");
        if(!params.containsKey(ROLL_NO) || !findInputType(params.get(ROLL_NO).toString()).equals(ROLL_NO))
            throw new BadRequestException("Invalid rollNo");

        if(params.containsKey(EMAIL_Id) && !params.get(EMAIL_Id).toString().isEmpty() && !findInputType(params.get(EMAIL_Id).toString()).equals(EMAIL_Id))
            throw new BadRequestException("Invalid emailId");
        if(params.containsKey(PHONE_NO) && !params.get(PHONE_NO).toString().isEmpty() && !findInputType(params.get(PHONE_NO).toString()).equals(ROLL_NO))
            throw new BadRequestException("Invalid phone no");

        if(!params.containsKey(BRANCH))
            throw new BadRequestException("Invalid Branch");

        try{
            Branch.valueOf(params.get(BRANCH).toString().toUpperCase());
        } catch (Exception e){
            throw new BadRequestException("Invalid Branch");
        }

        if(!params.containsKey(YEAR))
            throw new BadRequestException("Invalid Year");

        try{
            Year.valueOf(params.get(YEAR).toString().toUpperCase());
        } catch (Exception e){
            throw new BadRequestException("Invalid Year");
        }

    }

    public void deleteStudentById(Long id) throws BadRequestException {
        if (id == null || id <= 0) {
            throw new BadRequestException("Invalid student ID");
        }

        try{
            studentBuilder.deleteStudentById(id);
            log.info("deleted student with id {}",id);
        }catch (Exception e){
            throw new BadRequestException("Unsuccessful");
        }
    }


}
