package com.filmreview.models;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.filmreview.compositeKeys.FilmDirectorKey;

@Entity
public class FilmDirector implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Composite Key using filmId and directorId primary keys
	@EmbeddedId
	private FilmDirectorKey id = new FilmDirectorKey();
	
	//Relation with the Film entity
	@ManyToOne
	@MapsId("filmId")
	@JoinColumn(name = "filmId")
	private Film film;
		
	//Relation with the Director entity
	@ManyToOne
	@MapsId("directorId")
	@JoinColumn(name = "directorId")
	private Director director;
	
	//Getters and Setters
	public FilmDirectorKey getId() {
		return id;
	}

	public void setId(FilmDirectorKey id) {
		this.id = id;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}
}
