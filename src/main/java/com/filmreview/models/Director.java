package com.filmreview.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Director {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short directorId;
	
	private String name;
	
	//Relation with FilmDirector Entity
	@OneToMany(mappedBy = "director")
	private List<FilmDirector> films;

	
	//Constructors
	public Director() {}
	
	public Director(String name) {
		this.name = name;
	}
	
	//Getters and Setters
	public Short getDirectorId() {
		return directorId;
	}

	public void setDirectorId(Short directorId) {
		this.directorId = directorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FilmDirector> getFilms() {
		return films;
	}

	public void setFilms(List<FilmDirector> films) {
		this.films = films;
	}
	
	//Method to add a new film to the list of films 
	//Method to add a new genre to the list of genres
	public void addfilms(FilmDirector film) {
		this.films.add(film);
	}
}
