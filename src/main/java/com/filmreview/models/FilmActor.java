package com.filmreview.models;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.filmreview.compositeKeys.FilmActorKey;

@Entity
public class FilmActor implements Serializable{
	
	//Composite Key using filmId and actorId primary keys
	@EmbeddedId
	private FilmActorKey id = new FilmActorKey();
		
	//Relation with the Film entity
	@ManyToOne
	@MapsId("filmId")
	@JoinColumn(name = "filmId")
	private Film film;
			
	//Relation with the Actor entity
	@ManyToOne
	@MapsId("actorId")
	@JoinColumn(name = "actorId")
	private Actor actor;

	//Constructor
	public FilmActor() {}
	
	//Getters and Setters
	public FilmActorKey getId() {
		return id;
	}

	public void setId(FilmActorKey id) {
		this.id = id;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}
	
	

}
