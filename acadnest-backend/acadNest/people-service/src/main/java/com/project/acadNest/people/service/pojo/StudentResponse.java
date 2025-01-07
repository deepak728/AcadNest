package com.project.acadNest.people.service.pojo;

import com.project.acadNest.people.service.constant.Branch;
import com.project.acadNest.people.service.constant.Year;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class StudentResponse {
    long id;
    String name;
    long rollNo;
    Branch branch;
    Year year;
    String photo;
}
