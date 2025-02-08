package com.project.acadNest.auth.service.pojo;

import lombok.*;

@Data
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class AuthResponsePojo {
    String jwt;
    //Student student;
    Boolean isSuccessFul;
    String message;
}
