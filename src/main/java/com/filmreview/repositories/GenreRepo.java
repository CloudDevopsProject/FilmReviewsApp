package com.filmreview.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.models.Genre;

public interface GenreRepo extends JpaRepository<Genre, Short>{
	
	Optional<Genre> findByNameIgnoreCase(String name);

}
