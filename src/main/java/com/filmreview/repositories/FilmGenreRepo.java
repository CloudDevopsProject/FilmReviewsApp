package com.filmreview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.compositeKeys.FilmGenreKey;
import com.filmreview.models.FilmGenre;

public interface FilmGenreRepo extends JpaRepository<FilmGenre, FilmGenreKey>{

}
