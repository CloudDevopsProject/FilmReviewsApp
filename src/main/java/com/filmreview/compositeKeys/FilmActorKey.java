package com.filmreview.compositeKeys;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class FilmActorKey implements Serializable{
	
	//The two instance variables below will be used to create a primary key for the FilmDirector entity
	private Short actorId;
	private Short filmId;
	
	//Getters and Setters
	public Short getActorId() {
		return actorId;
	}
	public void setActorId(Short actorId) {
		this.actorId = actorId;
	}
	public Short getFilmId() {
		return filmId;
	}
	public void setFilmId(Short filmId) {
		this.filmId = filmId;
	}
}
