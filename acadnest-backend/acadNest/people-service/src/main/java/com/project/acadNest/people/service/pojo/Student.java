package com.project.acadNest.people.service.pojo;

import com.project.acadNest.people.service.constant.Branch;
import com.project.acadNest.people.service.constant.Year;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@Component
public class Student {
    long id;
    String name;
    long rollNo;
    String emailId;
    Branch branch;
    Year year;
    String photo;
    String phoneNo;

}
