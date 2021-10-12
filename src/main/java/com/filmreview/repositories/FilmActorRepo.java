package com.filmreview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.compositeKeys.FilmActorKey;
import com.filmreview.models.FilmActor;

public interface FilmActorRepo extends JpaRepository<FilmActor, FilmActorKey>{

}
