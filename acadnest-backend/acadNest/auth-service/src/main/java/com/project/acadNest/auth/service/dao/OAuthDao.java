package com.project.acadNest.auth.service.dao;

import com.project.acadNest.auth.service.model.MUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthDao extends JpaRepository<MUser,Long> {
}
