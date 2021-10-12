package com.filmreview.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.filmreview.models.User;
import com.filmreview.repositories.UserRepo;

public class AppUserDetailsService implements UserDetailsService{
	
	//Instantiate the User repo
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//Load the user using their email address
		User user = userRepo.findByEmail(email);
		//If there is no user matching the email address
		if(user == null) {
			throw new UsernameNotFoundException("There was an issue locating this user account");
		} 
		//If a user is found, instantiate the AppUserDetails class using the user instance
		return new AppUserDetails(user);
	}
	
	

}
