package com.filmreview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.models.FilmPhoto;

public interface FilmPhotoRepo extends JpaRepository<FilmPhoto, Long> {

}
