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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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

}
