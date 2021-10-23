package com.filmreview.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Genre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short genreId;
	
	String name;
	
	//Relation with FilmGenre Entity
	@OneToMany(mappedBy = "genre")
	private List<FilmGenre> films;
	
	//Getters and Setters
	public Short getGenreId() {
		return genreId;
	}

	public void setGenreId(Short genreId) {
		this.genreId = genreId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FilmGenre> getFilms() {
		return films;
	}

	public void setFilms(List<FilmGenre> films) {
		this.films = films;
	}
}
