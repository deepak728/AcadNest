package com.project.acadNest.auth.service.pojo;


import com.project.acadNest.auth.service.constant.ROLE;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class User {
    Long id;
    String emailId;
    String password;
    String googleId;
    ROLE role;
    String token;
    LocalDateTime createdOn;
    LocalDateTime updatedOn;
}
