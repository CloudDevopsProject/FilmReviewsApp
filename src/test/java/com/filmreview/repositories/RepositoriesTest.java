package com.filmreview.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.filmreview.controller.AppController;
import com.filmreview.models.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class RepositoriesTest {
    
    //Instantiate the repositories
    @Autowired
    private UserRepo userRepo;
    
    @Test
    void testGetUserByEmail() {
    	String userEmail = "testEmail@test.ie";
    	User user = new User("TestFirstName", "TestLastName", userEmail, "testPassword", null);
    	
    	User savedUser = userRepo.save(user);
    	
    	User retrievedUser = userRepo.findByEmail(userEmail);
    	
    	assertThat(retrievedUser.getUserId()).isEqualTo(savedUser.getUserId());   	
    }
    
    @Test
    void preventDuplicateEmailRegistration() {
    	User userOne = new User("TestFirstName", "TestLastName", "myEmailAddress@email.ie", "testPassword", null);
    	User userTwo = new User("TestFirstName", "TestLastName", "myEmailAddress@email.ie", "testPassword", null);
    	
    	userRepo.save(userOne);
    	User savedUserTwo = userRepo.save(userTwo);
    	
    	assertThat(userRepo.getById(savedUserTwo.getUserId())).isEqualTo(null);
    }



}
