package com.filmreview.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.models.Film;

public interface FilmRepo extends JpaRepository<Film, Short>{
	
	//Method below will allow film repo to be checked for a film matching name provided. Returns null if no matches
	Optional<Film> findByNameIgnoreCase(String name);
	

}
