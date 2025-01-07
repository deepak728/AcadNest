package com.project.acadNest.people.service.transformer;

import com.project.acadNest.people.service.model.MStudent;
import com.project.acadNest.people.service.pojo.Student;
import com.project.acadNest.people.service.pojo.StudentResponse;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StudentTransformer {

    // Transform Entity to POJO
    public static Student toPojo(MStudent mStudent) {
        if (mStudent == null) {
            return null;
        }

        Student student = new Student();
        student.setId(mStudent.getId());
        student.setName(mStudent.getName());
        student.setRollNo(mStudent.getRollNo());
        student.setEmailId(mStudent.getEmailId());
        student.setBranch(mStudent.getBranch());
        student.setYear(mStudent.getYear());
        student.setPhoto(mStudent.getPhoto());
        student.setPhoneNo(mStudent.getPhoneNo());

        return student;
    }

    // Transform POJO to Entity
    public static MStudent toEntity(Student student) {
        if (student == null) {
            return null;
        }

        MStudent mStudent = new MStudent();
        mStudent.setName(student.getName());
        mStudent.setRollNo(student.getRollNo());
        mStudent.setEmailId(student.getEmailId());
        mStudent.setBranch(student.getBranch());
        mStudent.setYear(student.getYear());
        mStudent.setPhoto(student.getPhoto());
        mStudent.setPhoneNo(student.getPhoneNo());

        return mStudent;
    }

    public static List<Student> toPojoList(List<MStudent> mStudents) {
        if (mStudents == null || mStudents.isEmpty()) {
            return Collections.emptyList();
        }

        return mStudents.stream()
                .map(StudentTransformer::toPojo)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static List<MStudent> toEntityList(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return Collections.emptyList();
        }

        return students.stream()
                .map(StudentTransformer::toEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static StudentResponse toStudentResponse(Student student) {
        if (student == null) {
            return null;
        }

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(student.getId());
        studentResponse.setName(student.getName());
        studentResponse.setRollNo(student.getRollNo());
        studentResponse.setBranch(student.getBranch());
        studentResponse.setYear(student.getYear());
        studentResponse.setPhoto(student.getPhoto());

        return studentResponse;
    }

    public static List<StudentResponse> toStudentResponseList(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return Collections.emptyList();
        }

        return students.stream()
                .map(student -> toStudentResponse(student))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
