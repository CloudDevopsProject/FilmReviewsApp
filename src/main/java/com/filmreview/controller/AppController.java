package com.filmreview.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.filmreview.authentication.AppUserDetails;
import com.filmreview.models.Actor;
import com.filmreview.models.Contact;
import com.filmreview.models.Director;
import com.filmreview.models.Film;
import com.filmreview.models.FilmActor;
import com.filmreview.models.FilmDirector;
import com.filmreview.models.FilmGenre;
import com.filmreview.models.FilmReview;
import com.filmreview.models.Genre;
import com.filmreview.models.User;
import com.filmreview.repositories.ActorRepo;
import com.filmreview.repositories.ContactRepo;
import com.filmreview.repositories.DirectorRepo;
import com.filmreview.repositories.FilmActorRepo;
import com.filmreview.repositories.FilmDirectorRepo;
import com.filmreview.repositories.FilmGenreRepo;
import com.filmreview.repositories.FilmRepo;
import com.filmreview.repositories.FilmReviewRepo;
import com.filmreview.repositories.GenreRepo;
import com.filmreview.repositories.UserRepo;
import com.filmreview.repositories.UserRoleRepo;

@Controller
public class AppController {

	// Repositories
	
	@Autowired
	ContactRepo contactRepo;
	
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

	@Autowired
	FilmReviewRepo filmReviewRepo;

	@Autowired
	FilmGenreRepo filmGenreRepo;

	@Autowired
	FilmDirectorRepo filmDirectorRepo;

	@Autowired
	FilmActorRepo filmActorRepo;

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

	//Method for contact page
	@GetMapping("/contact")
	public String contact(Model model) {
		model.addAttribute("title", "Contact Us");
		model.addAttribute("contact", new Contact());
		return "contact.html";
	}
	
	//processing contact form
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact) {
		
		//Contact contact1 = new Contact();

		contactRepo.save(contact);
		
		
		System.out.println("Data "+ contact);
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

	// Method below will try to create a new review based on the details provided in
	// the review form
	@PostMapping("/addReview")
	public String addReview(@RequestParam("film") String filmName, @RequestParam("genre") Short[] genre,
			@RequestParam("director") Short[] director, @RequestParam("actor") Short[] actor,
			@RequestParam("releaseYear") String releaseYear, @RequestParam("rating") Short rating,
			@RequestParam("textArea") String reviewContent, RedirectAttributes attributes, Authentication auth) {
		String pageMessage;
		// Make sure the user is logged in
		if (!getUserRole().equals("userNotLoggedIn")) {
			// Instantiate the FilmReview class and set it's review and rating values
			FilmReview review = new FilmReview(reviewContent, rating);
			// Try to set the reviews user details
			try {
				AppUserDetails details = (AppUserDetails) auth.getPrincipal();
				Long userId = details.getUserId();
				User user = userRepo.getById(userId);
				review.setUserId(user);
				filmReviewRepo.save(review);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("There was an issue linking the user to the review posted");
			}

			// Get or Create the films details
			Film film;
			try {
				// Either create a new film instance for the review or retrieve its details from
				// database if it already exists
				try {
					// If film isn't already in the database, add it
					if (!filmRepo.findByNameIgnoreCase(filmName).isPresent()) {
						film = new Film(filmName, releaseYear);
						filmRepo.save(film);
					} else {
						// If the film is already in the database, retrieve it
						film = filmRepo.findByNameIgnoreCase(filmName).get();
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("There was an issue setting the film details");
					return "index.html";
				}

				// Set the films genre details
				try {
					// If the films genre list is empty create a new list
					List<FilmGenre> existingGenreList;
					if (film.getGenres() == null) {
						existingGenreList = new ArrayList<>();
					} else {
						existingGenreList = film.getGenres();
					}
					// Set the films genre(s) by traversing all genres provided by user
					for (int i = 0; i < genre.length; i++) {
						// If there isn't already a record linking the film and genre, create one
						if (filmGenreRepo.findFilmGenreByFilmAndGenre(film.getFilmId(), genre[i]) == null) {
							// Create a FilmGenre instance using the film and genre details provided
							FilmGenre filmGenre = new FilmGenre();
							filmGenre.setFilm(film);
							filmGenre.setGenre(genreRepo.getById(genre[i]));
							filmGenreRepo.save(filmGenre);
							existingGenreList.add(filmGenre);
							genreRepo.getById(genre[i]).addFilm(filmGenre);
							genreRepo.save(genreRepo.getById(genre[i]));
						}
					}
					film.setGenres(existingGenreList);
					filmRepo.save(film);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("There was an issue linking this film with it's selected genres");
				}

				// Set the film director details
				try {
					// If the films director list is empty create a new list
					List<FilmDirector> existingDirectorsList;
					if (film.getDirectors() == null) {
						existingDirectorsList = new ArrayList<>();
					} else {
						existingDirectorsList = film.getDirectors();
					}
					// Set the films Director(s) by traversing all directors provided by user
					for (int i = 0; i < director.length; i++) {
						// If there isn't already a record linking the film and director, create one
						if (filmDirectorRepo.findFilmDirectorByFilmAndDirector(film.getFilmId(), director[i]) == null) {
							// Create a FilmDirector instance using the film and director details provided
							FilmDirector filmDirector = new FilmDirector();
							filmDirector.setFilm(film);
							filmDirector.setDirector(directorRepo.getById(director[i]));
							filmDirectorRepo.save(filmDirector);
							existingDirectorsList.add(filmDirector);
							directorRepo.getById(director[i]).addfilms(filmDirector);
							directorRepo.save(directorRepo.getById(director[i]));
						}
					}
					film.setDirectors(existingDirectorsList);
					filmRepo.save(film);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("There was an issue linking this film with it's selected directors");
				}

				// Set the film actor details
				try {
					// If the films actor list is empty create a new list
					List<FilmActor> existingActorsList;
					if (film.getActors() == null) {
						existingActorsList = new ArrayList<>();
					} else {
						existingActorsList = film.getActors();
					}
					// Set the films actor(s) by traversing all actors provided by user
					for (int i = 0; i < actor.length; i++) {
						// If there isn't already a record linking the film and actor, create one
						if (filmActorRepo.findFilmActorByFilmAndActor(film.getFilmId(), actor[i]) == null) {
							// Create a FilmActor instance using the film and actor details provided
							FilmActor filmActor = new FilmActor();
							filmActor.setFilm(film);
							filmActor.setActor(actorRepo.getById(actor[i]));
							filmActorRepo.save(filmActor);
							existingActorsList.add(filmActor);
							actorRepo.getById(actor[i]).addFilm(filmActor);
							actorRepo.save(actorRepo.getById(actor[i]));
						}
					}
					film.setActors(existingActorsList);
					filmRepo.save(film);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("There was an issue linking this film with it's selected actors");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("There was an issue with creating a record for chosen film");
				return "index.html";
			}

			// Link the film to the review
			try {
				review.setFilmId(film);
				filmReviewRepo.save(review);
				// If the review list for the film is empty create a new review list and add the
				// review to it
				List<FilmReview> existingReviewsList;
				if (film.getReviews() == null) {
					existingReviewsList = new ArrayList<>();
					existingReviewsList.add(review);
					film.setReviews(existingReviewsList);
				} else {
					existingReviewsList = film.getReviews();
					existingReviewsList.add(review);
				}
				filmRepo.save(film);

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("There was an linking the review and the film");
			}
		}
		return "index.html";
	}

	// Method to add a genre to the database
	@PostMapping("/addGenre")
	public String addGenre(@RequestParam("valueToBeAdded") String newGenre, RedirectAttributes attributes) {
		String pageMessage;
		// Make sure the user is logged in
		if (!getUserRole().equals("userNotLoggedIn")) {
			try {
				// If the genre is not already in the database, add it
				if (!genreRepo.findByNameIgnoreCase(newGenre).isPresent()) {
					Genre genre = new Genre(newGenre);
					genreRepo.save(genre);
					pageMessage = newGenre + " has been added to our database for selection";
				} else {
					// If genre is already in the database, display that information to the user
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

	// Method to add a director to the database
	@PostMapping("/addDirector")
	public String addDirector(@RequestParam("valueToBeAdded") String newDirector, RedirectAttributes attributes) {
		String pageMessage;
		// Make sure the user is logged in
		if (!getUserRole().equals("userNotLoggedIn")) {
			try {
				// If the director is not already in the database, add it
				if (!directorRepo.findByNameIgnoreCase(newDirector).isPresent()) {
					Director director = new Director(newDirector);
					directorRepo.save(director);
					pageMessage = newDirector + " has been added to our database for selection";
				} else {
					// If director is already in the database, display that information to the user
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

	// Method to add an actor to the database
	@PostMapping("/addActor")
	public String addActor(@RequestParam("valueToBeAdded") String newActor, RedirectAttributes attributes) {
		String pageMessage;
		// Make sure the user is logged in
		if (!getUserRole().equals("userNotLoggedIn")) {
			try {
				// If the actor is not already in the database, add it
				if (!actorRepo.findByNameIgnoreCase(newActor).isPresent()) {
					Actor actor = new Actor(newActor);
					actorRepo.save(actor);
					pageMessage = newActor + " has been added to our database for selection";
				} else {
					// If actor is already in the database, display that information to the user
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
