package com.filmreview.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.models.Director;

public interface DirectorRepo extends JpaRepository<Director, Short>{
	
	Optional<Director> findByNameIgnoreCase(String name);

}
