package com.filmreview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.filmreview.models.User;
import com.filmreview.repositories.UserRepo;
import com.filmreview.repositories.UserRoleRepo;

@Controller
public class AppController {

	// Repositories
	@Autowired
	UserRoleRepo userRoleRepo;

	@Autowired
	UserRepo userRepo;

	// Method below will display the home page
	@GetMapping("/")
	public String displayHomePage() {
		return "index.html";
	}
	
	//Method below will display the login form
	@GetMapping("/displayLogin")
	public String displayLoginForm(RedirectAttributes attributes) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//If user is not already logged in, display the login page
		if(auth instanceof AnonymousAuthenticationToken) {
			return "login.html";
		} else {
		//If user is already logged in display a message and redirect to the home page
			attributes.addFlashAttribute("pageMessage", "You are already logged in");
			return "redirect:/";
		}
	}
	
	//Method below will redirect to the login page if login not successful
	@GetMapping("/loginFailure")
	public String displayLoginError(RedirectAttributes attributes) {
		attributes.addFlashAttribute("pageMessage", "Login Failed");
		return "redirect:/displayLogin";
	}
	
	
	// Method below will display the registration form
	@GetMapping("/register")
	public String displayRegistrationPage() {
		return "registration.html";
	}

	// Method below will try to create a new user based on the details provided in the registration form
	@PostMapping("/register")
	public String registerUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email, 
								@RequestParam("password") String password, RedirectAttributes attributes) {
		//Encrypt the users password before it is saved to the database
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		password = passwordEncoder.encode(password);
		//postStatus will track whether the registration was successful or not
		String postStatus;
		//Only attempt to create a new User if there isn't already a user in the database that matches the email address submitted
		if(userRepo.findByEmail(email) == null) {
			try {
				User genericUser = new User(firstName, lastName, email, password, userRoleRepo.getById((short) 2));
				postStatus = "Registration Successful!";
				userRepo.save(genericUser);
			} catch(Exception e) {
				e.printStackTrace();
				postStatus = "There was an issue creating your account";
				attributes.addFlashAttribute("pageMessage", postStatus);
				return "redirect:/register";
			} 
		//If there is a user already linked to the email address 
		} else {
			postStatus = "Unable to create account. You may have already registered";
		}
		attributes.addFlashAttribute("pageMessage", postStatus);
		return "redirect:/displayLogin";		
	}

}
