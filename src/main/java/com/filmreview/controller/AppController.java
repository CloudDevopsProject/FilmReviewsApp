package com.filmreview.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.filmreview.models.Actor;
import com.filmreview.models.Director;
import com.filmreview.models.Film;
import com.filmreview.models.FilmReview;
import com.filmreview.models.Genre;
import com.filmreview.models.User;
import com.filmreview.repositories.ActorRepo;
import com.filmreview.repositories.DirectorRepo;
import com.filmreview.repositories.FilmRepo;
import com.filmreview.repositories.GenreRepo;
import com.filmreview.repositories.UserRepo;
import com.filmreview.repositories.UserRoleRepo;

@Controller
public class AppController {

	// Repositories
	@Autowired
	UserRoleRepo userRoleRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	FilmRepo filmRepo;

	@Autowired
	GenreRepo genreRepo;

	@Autowired
	DirectorRepo directorRepo;

	@Autowired
	ActorRepo actorRepo;

	// Method below determines user type
	public String getUserRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			String role = authentication.getAuthorities().toString();
			// Return the users role
			if (role.equals("[admin]")) {
				return "admin";
			} else if (role.equals("[genericUser]")) {
				return "genericUser";
			}
		}
		return "userNotLoggedIn";
	}

	// Method below will display the test page
	@GetMapping("/test")
	public String test() {
		return "test.html";
	}
	@GetMapping("/contact")
	public String contact() {
		return "contact.html";
	}

	// Method below will display the home page
	@GetMapping("/")
	public String displayHomePage() {
		return "index.html";
	}

	// Method below will display the login form
	@GetMapping("/displayLogin")
	public String displayLoginForm(RedirectAttributes attributes) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// If user is not already logged in, display the login page
		if (auth instanceof AnonymousAuthenticationToken) {
			return "login.html";
		} else {
			// If user is already logged in display a message and redirect to the home page
			attributes.addFlashAttribute("pageMessage", "You are already logged in");
			return "redirect:/";
		}
	}

	// Method below will redirect to the login page if login not successful
	@GetMapping("/loginFailure")
	public String displayLoginError(RedirectAttributes attributes) {
		attributes.addFlashAttribute("pageMessage", "Login Failed");
		return "redirect:/displayLogin";
	}

	// Method below will display the registration form
	@GetMapping("/register")
	public String displayRegistrationPage(RedirectAttributes attributes) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// If the user is not logged in, display the registration form
		if (auth instanceof AnonymousAuthenticationToken) {
			return "registration.html";
		} else {
			// If user is already logged in display a message and redirect to the home page
			attributes.addFlashAttribute("pageMessage", "Logged in users cannot register accounts");
			return "redirect:/";
		}
	}

	// Method below will try to create a new user based on the details provided in
	// the registration form
	@PostMapping("/register")
	public String registerUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("email") String email, @RequestParam("password") String password,
			RedirectAttributes attributes) {
		// Encrypt the users password before it is saved to the database
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		password = passwordEncoder.encode(password);
		// postStatus will track whether the registration was successful or not
		String postStatus;
		// Only attempt to create a new User if there isn't already a user in the
		// database that matches the email address submitted
		if (userRepo.findByEmail(email) == null) {
			try {
				User genericUser = new User(firstName, lastName, email, password, userRoleRepo.getById((short) 2));
				postStatus = "Registration Successful!";
				userRepo.save(genericUser);
			} catch (Exception e) {
				e.printStackTrace();
				postStatus = "There was an issue creating your account";
				attributes.addFlashAttribute("pageMessage", postStatus);
				return "redirect:/register";
			}
			// If there is a user already linked to the email address
		} else {
			postStatus = "Unable to create account. You may have already registered";
		}
		attributes.addFlashAttribute("pageMessage", postStatus);
		return "redirect:/displayLogin";
	}

	// Method below will display page displaying all generic users if the user is an
	// admin
	@GetMapping("/displayUsers")
	public String displayUsers(Model model) {
		// Make sure the logged in user is an admin before accessing this page
		if (getUserRole().equals("admin")) {
			// Get all users from the database and add them to the page model
			List<User> users = userRepo.findAll();
			// Determine if there are users listed in the database
			if (users.isEmpty()) {
				model.addAttribute("users", "emptyRepo");
			} else {
				model.addAttribute("users", users);
			}
			return "viewUsers.html";
		} else {
			return null;
		}

	}

	// Method below will allow admins to adjust user roles
	@GetMapping("/adjustRole/{userId}")
	public String adjustRole(@PathVariable("userId") Long userId, RedirectAttributes attribute) {
		// Make sure the user is a logged in user
		if (getUserRole().equals("admin")) {
			String adjustStatus;
			// Retrieve the desired user from the repo using the userID argument. Try/Catch
			// block due to possibility userId doesn't exist
			try {
				User user = userRepo.getById(userId);
				// Change the user's role id to the opposite of what it currently is
				if (user.getRoleId().getRoleID() == 1) {
					user.setRoleId(userRoleRepo.getById((short) 2));
					adjustStatus = "Admin rights revoked from " + user.getFullName();
				} else {
					user.setRoleId(userRoleRepo.getById((short) 1));
					adjustStatus = "Admin rights granted to " + user.getFullName();
				}
				// Save the updated user information
				userRepo.save(user);
			} catch (Exception e) {
				// If there is no user matching id provided
				e.printStackTrace();
				adjustStatus = "Error: No user matches user id provided";
			}
			attribute.addFlashAttribute("pageMessage", adjustStatus);
			return "redirect:/displayUsers";
		} else {
			return null;
		}
	}

	// Method below will display review form
	@GetMapping("/addReview")
	public String displayReviewForm(Model model) {
		// Make sure the user is logged in
		if (!getUserRole().equals("userNotLoggedIn")) {
			// Get all films already in the database and add to the model
			List<Film> films = filmRepo.findAll();
			// Identify if the movie repo is empty
			if (films.isEmpty()) {
				model.addAttribute("films", "emptyRepo");
			} else {
				// Add movies from the movie repo to the model if they exist
				model.addAttribute("films", films);
			}
			// Get all genres already in the database and add to the model
			List<Genre> genres = genreRepo.findAll();
			// Identify if the genre repo is empty
			if (genres.isEmpty()) {
				model.addAttribute("genres", "emptyRepo");
			} else {
				// Add genres from the genre repo to the model if they exist
				model.addAttribute("genres", genres);
			}
			// Get all directors already in the database and add to the model
			List<Director> directors = directorRepo.findAll();
			// Identify if the director repo is empty
			if (directors.isEmpty()) {
				model.addAttribute("directors", "emptyRepo");
			} else {
				// Add directors from the director repo to the model if they exist
				model.addAttribute("directors", directors);
			}
			// Get all actors already in the database and add to the model
			List<Actor> actors = actorRepo.findAll();
			// Identify if the actor repo is empty
			if (actors.isEmpty()) {
				model.addAttribute("actors", "emptyRepo");
			} else {
				// Add actors from the actor repo to the model if they exist
				model.addAttribute("actors", actors);
			}
			// Get the current year and add to the model to provide a threshold on films
			// release year
			LocalDate date = LocalDate.now();
			model.addAttribute("yearThreshold", date.getYear());
			return "reviewForm.html";
		} else {
			return "redirect:/";
		}
	}

	// Method below will try to create a new review based on the details provided in the review form
	@PostMapping("/addReview")
	public String addReview(@RequestParam("film") String filmName, @RequestParam("genre") String genre,
			@RequestParam("director") String director, @RequestParam("actor") String actor, @RequestParam("releaseYear") String releaseYear, 
			@RequestParam("rating") Short rating, @RequestParam("textArea") String reviewContent, RedirectAttributes attributes) {
		// Make sure the user is logged in
		if (!getUserRole().equals("userNotLoggedIn")) {
			String reviewStatus;
			//Instantiate the FilmReview class
			FilmReview review;
			try {
				review = new FilmReview(reviewContent, rating);
			}catch(Exception e) {
				reviewStatus = "An error has occurred";
				return "index.html";
			}
			//Film Details for the review
			try {
				//Check if film already exists in the film repo, add film to the film reviews details
				 if(!filmRepo.findByName(filmName).isPresent()) { 
					 //If the film is not in the database already, create a record for it 
					 Film film = new Film(filmName, releaseYear);
					 review.setFilmId(film);
				 } else {
					//If the film is in the database, retrieve it
					Optional<Film> film = filmRepo.findByName(filmName);
					review.setFilmId(film.get());
				}
			} catch(Exception e) {
				reviewStatus = "An error has occured";
				return "index.html";
			}
			//User details for the review
			try {
				//User user = userRepo.getById(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
			} catch(Exception e) {
				reviewStatus = "An error has occured";
				return "index.html";
			}
		}
		return "index.html";
	}
	
	//Method to add a genre to the database
	@PostMapping("/addGenre")
	public String addGenre(@RequestParam("valueToBeAdded") String newGenre, RedirectAttributes attributes) {
		String pageMessage;
		//Make sure the user is logged in
		if (!getUserRole().equals("userNotLoggedIn")) {
			try {
				//If the genre is not already in the database, add it
				if(!genreRepo.findByNameIgnoreCase(newGenre).isPresent()) {
					Genre genre = new Genre(newGenre);
					genreRepo.save(genre);
					pageMessage = newGenre + " has been added to our database for selection";
				} else {
					//If genre is already in the database, display that information to the user
					pageMessage = "This genre is already in our database";
				}
			} catch (Exception e) {
				e.printStackTrace();
				pageMessage = "An error has occurred with this request";
			}
			attributes.addFlashAttribute("pageMessage", pageMessage);
			return "redirect:/addReview";
		} else {
			pageMessage = "An error has occurred";
			attributes.addFlashAttribute("pageMessage", pageMessage);
			return "index";
		}
	}
	
	//Method to add a director to the database
	@PostMapping("/addDirector")
	public String addDirector(@RequestParam("valueToBeAdded") String newDirector, RedirectAttributes attributes) {
		String pageMessage;
		//Make sure the user is logged in
		if (!getUserRole().equals("userNotLoggedIn")) {
			try {
				//If the director is not already in the database, add it
				if(!directorRepo.findByNameIgnoreCase(newDirector).isPresent()) {
					Director director = new Director(newDirector);
					directorRepo.save(director);
					pageMessage = newDirector + " has been added to our database for selection";
				} else {
					//If director is already in the database, display that information to the user
					pageMessage = "This director is already in our database";
				}
			} catch (Exception e) {
				e.printStackTrace();
				pageMessage = "An error has occurred with this request";
			}
			attributes.addFlashAttribute("pageMessage", pageMessage);
			return "redirect:/addReview";
		} else {
			pageMessage = "An error has occurred";
			attributes.addFlashAttribute("pageMessage", pageMessage);
			return "index";
		}
	}
	
	//Method to add a director to the database
	@PostMapping("/addActor")
	public String addActor(@RequestParam("valueToBeAdded") String newActor, RedirectAttributes attributes) {
		String pageMessage;
		//Make sure the user is logged in
		if (!getUserRole().equals("userNotLoggedIn")) {
			try {
				//If the actor is not already in the database, add it
				if(!actorRepo.findByNameIgnoreCase(newActor).isPresent()) {
					Actor actor = new Actor(newActor);
					actorRepo.save(actor);
					pageMessage = newActor + " has been added to our database for selection";
				} else {
					//If actor is already in the database, display that information to the user
					pageMessage = "This actor is already in our database";
				}
			} catch (Exception e) {
				e.printStackTrace();
				pageMessage = "An error has occurred with this request";
			}
			attributes.addFlashAttribute("pageMessage", pageMessage);
			return "redirect:/addReview";
		} else {
			pageMessage = "An error has occurred";
			attributes.addFlashAttribute("pageMessage", pageMessage);
			return "index";
		}
	}
}
