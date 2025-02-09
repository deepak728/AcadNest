package com.project.acadNest.people.service.builder;

import com.project.acadNest.people.service.model.MStudent;
import com.project.acadNest.people.service.dao.StudentDao;
import com.project.acadNest.people.service.pojo.Student;
import com.project.acadNest.people.service.transformer.StudentTransformer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class StudentBuilder {

    private final StudentDao studentDao;

    public List<Student> findStudentByNameRegex(String name) {
        Optional<List<MStudent>> mStudents = studentDao.findByNameRegex(name);
        if (mStudents.isPresent()) {
            return StudentTransformer.toPojoList(mStudents.get());
        } else {
            log.error("Students not found for name: " + name);
            return Collections.emptyList();
        }
    }

    public List<Student> findStudentByEmailRegex(String emailId) {
        Optional<List<MStudent>> mStudents = studentDao.findByEmailIdRegex(emailId);
        if (mStudents.isPresent()) {
            return StudentTransformer.toPojoList(mStudents.get());
        } else {
            log.error("Students not found for emailId: " + emailId);
            return Collections.emptyList();
        }
    }


    public List<Student> findStudentByRollNoRegex(String rollNo) {
        Optional<List<MStudent>> mStudents = studentDao.findByRollNoRegex(rollNo);
        if (mStudents.isPresent()) {
            return StudentTransformer.toPojoList(mStudents.get());
        } else {
            log.error("Students not found for rollNo: " + rollNo);
            return Collections.emptyList();
        }
    }

    public Student save(Student student){
        if(student==null) return null;
        return StudentTransformer.toPojo(studentDao.save(StudentTransformer.toEntity(student)));
    }

    public void deleteStudentById(Long id){
        studentDao.deleteById(id);
    }

    public Student findByEmailId(String emailId){
        Optional<MStudent> mStudent = studentDao.findByEmailId(emailId);

        if(mStudent.isPresent()){
            log.info("MStudent : {}",mStudent);
            return StudentTransformer.toPojo(mStudent.get());
        }
        return null;
    }


}
