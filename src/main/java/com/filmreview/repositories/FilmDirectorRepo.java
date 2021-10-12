package com.filmreview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.compositeKeys.FilmDirectorKey;
import com.filmreview.models.FilmDirector;

public interface FilmDirectorRepo extends JpaRepository<FilmDirector, FilmDirectorKey>{

}
