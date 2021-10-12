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
}
