package com.filmreview.compositeKeys;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class FilmGenreKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//The two instance variables below will be used to create a primary key for the FilmGenre entity
	private Short genreId;
	private Short filmId;
	
	//Getters and Setters
	public Short getGenreId() {
		return genreId;
	}
	public void setGenreId(Short genreId) {
		this.genreId = genreId;
	}
	public Short getFilmId() {
		return filmId;
	}
	public void setFilmId(Short filmId) {
		this.filmId = filmId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
