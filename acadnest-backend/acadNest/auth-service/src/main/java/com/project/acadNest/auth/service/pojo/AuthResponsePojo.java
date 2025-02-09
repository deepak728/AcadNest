package com.project.acadNest.auth.service.pojo;

import com.project.acadNest.people.service.pojo.Student;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class AuthResponsePojo {
    String jwt;
    Student student;
    Boolean isSuccessFul;
    String message;
}
