package com.filmreview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.filmreview.compositeKeys.FilmDirectorKey;
import com.filmreview.models.FilmDirector;


public interface FilmDirectorRepo extends JpaRepository<FilmDirector, FilmDirectorKey>{
	
	//Signature below will allow FilmDirectorRepo to be queried with a film and director id value
	@Query(value="SELECT * FROM FilmDirector f WHERE f.filmId = ?1 and f.directorId = ?2", nativeQuery=true)
	FilmDirector findFilmDirectorByFilmAndDirector(Short filmId, Short directorId);

}
