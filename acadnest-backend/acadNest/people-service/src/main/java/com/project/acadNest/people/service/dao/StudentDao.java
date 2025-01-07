package com.project.acadNest.people.service.dao;

import com.project.acadNest.people.service.model.MStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentDao extends JpaRepository<MStudent,Long> {

    MStudent save(MStudent student);

    void deleteById(Long id);

    Optional<MStudent> findByName(String name);

    Optional<MStudent> findByRollNo(long rollNo);

    Optional<MStudent> findByEmailId(String emailId);

    // Search students by name using regex
    @Query(value = "SELECT * FROM students WHERE name ~* :regex", nativeQuery = true)
    Optional<List<MStudent>> findByNameRegex(@Param("regex") String regex);

    // Search students by email using regex
    @Query(value = "SELECT * FROM students WHERE email_id ~* :regex", nativeQuery = true)
    Optional<List<MStudent>> findByEmailIdRegex(@Param("regex") String regex);

    // Search students by rollNo using regex
    @Query(value = "SELECT * FROM students WHERE roll_no::text ~ :regex", nativeQuery = true)
    Optional<List<MStudent>> findByRollNoRegex(@Param("regex") String regex);


}
