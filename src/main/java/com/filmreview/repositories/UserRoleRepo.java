package com.filmreview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.models.UserRole;

public interface UserRoleRepo extends JpaRepository<UserRole, Short>{

}
