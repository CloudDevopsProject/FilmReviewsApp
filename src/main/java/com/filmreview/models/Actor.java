package com.filmreview.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Actor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short actorId;
	
	private String name;
	
	//Relation with FilmActor Entity
	@OneToMany(mappedBy = "actor")
	private List<FilmActor> films;
	
	//Constructors
	public Actor() {}
	
	public Actor(String name) {
		this.name = name;
	}
	
	//Getters and Setters
	public Short getActorId() {
		return actorId;
	}

	public void setActorId(Short actorId) {
		this.actorId = actorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FilmActor> getFilms() {
		return films;
	}

	public void setFilms(List<FilmActor> films) {
		this.films = films;
	}
	
	//Method below will add new FilmActor details
	public void addFilm(FilmActor filmActor) {
		this.films.add(filmActor);
	}
}
