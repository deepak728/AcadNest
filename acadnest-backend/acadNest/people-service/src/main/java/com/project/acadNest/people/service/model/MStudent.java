package com.project.acadNest.people.service.model;

import com.project.acadNest.people.service.constant.Branch;
import com.project.acadNest.people.service.constant.Year;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "students")
@ToString
@Getter
@Setter
@Data
@NoArgsConstructor
public class MStudent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate primary key
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "roll_no", nullable = false, unique = true)
    private long rollNo;

    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    @Enumerated(EnumType.STRING)
    @Column(name = "branch", nullable = false)
    private Branch branch;

    @Enumerated(EnumType.STRING)
    @Column(name = "year", nullable = false)
    private Year year;

    @Column(name = "photo") // Photo URL or base64 string
    private String photo;

    @Column(name = "phone_no", nullable = false, unique = true)
    private String phoneNo;
}
