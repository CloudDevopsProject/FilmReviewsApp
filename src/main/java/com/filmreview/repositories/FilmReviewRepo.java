package com.filmreview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.models.FilmReview;

public interface FilmReviewRepo extends JpaRepository<FilmReview, Long>{
	
}
