package com.project.acadNest.auth.service.dao;

import com.project.acadNest.auth.service.model.MUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthDao extends JpaRepository<MUser,Long> {

    MUser save(MUser student);

    void deleteById(Long id);

    @Query(value = "SELECT * FROM USERS WHERE email = :emailId", nativeQuery = true)
    Optional<MUser> findUserByEmail(@Param("emailId") String emailId);


}
