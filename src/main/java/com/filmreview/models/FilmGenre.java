package com.filmreview.models;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;


import com.filmreview.compositeKeys.FilmGenreKey;

@Entity
public class FilmGenre implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Composite Key using filmId and genreId primary keys
	@EmbeddedId
	private FilmGenreKey id = new FilmGenreKey();
		
	//Relation with the Film entity
	@ManyToOne
	@MapsId("filmId")
	@JoinColumn(name = "filmId")
	private Film film;
			
	//Relation with the Genre entity
	@ManyToOne
	@MapsId("genreId")
	@JoinColumn(name = "genreId")
	private Genre genre;

	public FilmGenreKey getId() {
		return id;
	}

	public void setId(FilmGenreKey id) {
		this.id = id;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
}
