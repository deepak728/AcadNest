package com.project.acadNest.people.service.component;


import com.project.acadNest.people.service.builder.StudentBuilder;
import com.project.acadNest.people.service.dao.StudentDao;
import com.project.acadNest.people.service.pojo.Student;
import com.project.acadNest.people.service.pojo.StudentResponse;
import com.project.acadNest.people.service.transformer.StudentTransformer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class StudentComponent {

    private final StudentBuilder studentBuilder;

    public List<StudentResponse> findStudent(Map<String,Object> params) throws BadRequestException {

        if(params==null || params.size()==0)
            throw new BadRequestException("Invalid payload");
        List<Student> studentList = null;

        if(params.containsKey("name"))
            studentList = studentBuilder.findStudentByNameRegex(params.get("name").toString());
        else if(params.containsKey("emailId"))
            studentList = studentBuilder.findStudentByEmailRegex(params.get("emailId").toString());
        else if(params.containsKey("rollNo"))
            studentList = studentBuilder.findStudentByRollNoRegex(params.get("rollNo").toString());

        log.info("fetched students list :{}",studentList.toString());

        return StudentTransformer.toStudentResponseList(studentList);

    }
}
