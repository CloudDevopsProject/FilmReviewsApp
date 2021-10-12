package com.filmreview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.models.Film;

public interface FilmRepo extends JpaRepository<Film, Short>{

}
