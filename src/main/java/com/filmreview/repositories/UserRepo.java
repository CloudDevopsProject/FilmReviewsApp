package com.filmreview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.models.User;

public interface UserRepo extends JpaRepository<User, Long>{
	
	//Get a User instance based on their email address
	User findByEmail(String emailAddress);

}
