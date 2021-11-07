package com.filmreview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.filmreview.compositeKeys.FilmActorKey;
import com.filmreview.models.FilmActor;


public interface FilmActorRepo extends JpaRepository<FilmActor, FilmActorKey>{
	
	//Signature below will allow FilmActorRepo to be queried with a film and actor id value
	@Query(value="SELECT * FROM FilmActor f WHERE f.filmId = ?1 and f.actorId = ?2", nativeQuery=true)
	FilmActor findFilmActorByFilmAndActor(Short filmId, Short actorId);

}
