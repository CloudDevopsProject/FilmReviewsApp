package com.filmreview.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Film {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short filmId;
	
	private String name;
	private String releaseYear;
	
	//Relation with FilmReview entity
	@OneToMany(mappedBy = "filmId")
	private List<FilmReview> reviews;
	
	//Relation with FilmDirector Entity
	@OneToMany(mappedBy = "film")
	private List<FilmDirector> directors;
	
	//Relation with the FilmGenre Entity
	@OneToMany(mappedBy = "film")
	private List<FilmGenre> genres;
	
	//Relation with the FilmActor Entity
	@OneToMany(mappedBy = "film")
	private List<FilmActor> actors;
	
	//Getters and Setters
	public Short getFilmId() {
		return filmId;
	}

	public void setFilmId(Short filmId) {
		this.filmId = filmId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	public List<FilmReview> getReviews() {
		return reviews;
	}

	public void setReviews(List<FilmReview> reviews) {
		this.reviews = reviews;
	}

	public List<FilmDirector> getDirectors() {
		return directors;
	}

	public void setDirectors(List<FilmDirector> directors) {
		this.directors = directors;
	}

	public List<FilmGenre> getGenres() {
		return genres;
	}

	public void setGenres(List<FilmGenre> genres) {
		this.genres = genres;
	}

	public List<FilmActor> getActors() {
		return actors;
	}

	public void setActors(List<FilmActor> actors) {
		this.actors = actors;
	}
}
