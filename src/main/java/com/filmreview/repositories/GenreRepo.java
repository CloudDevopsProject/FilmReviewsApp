package com.filmreview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.models.Genre;

public interface GenreRepo extends JpaRepository<Genre, Short>{

}
