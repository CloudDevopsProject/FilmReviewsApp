package com.filmreview.compositeKeys;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class FilmDirectorKey implements Serializable{
	
	//The two instance variables below will be used to create a primary key for the FilmDirector entity
	private Short directorId;
	private Short filmId;
	
	//Getters and Setters
	public Short getDirectorId() {
		return directorId;
	}
	public void setDirectorId(Short directorId) {
		this.directorId = directorId;
	}
	public Short getFilmId() {
		return filmId;
	}
	public void setFilmId(Short filmId) {
		this.filmId = filmId;
	}
}
