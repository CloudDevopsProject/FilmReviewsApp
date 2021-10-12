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
	
	

}
