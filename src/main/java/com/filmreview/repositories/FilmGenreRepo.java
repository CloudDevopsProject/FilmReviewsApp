package com.filmreview.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.filmreview.compositeKeys.FilmGenreKey;
import com.filmreview.models.FilmGenre;

public interface FilmGenreRepo extends JpaRepository<FilmGenre, FilmGenreKey>{
	
	//Signature below will allow FilmGenreRepo to be queried with a film and genre id value
	@Query(value="SELECT * FROM FilmGenre f WHERE f.filmId = ?1 and f.genreId = ?2", nativeQuery=true)
	FilmGenre findFilmGenreByFilmAndGenre(Short filmId, Short genreId);

}
