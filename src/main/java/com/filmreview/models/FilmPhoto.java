package com.filmreview.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class FilmPhoto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long photoId;
	
	@Column(nullable = false)
	private String imageUrl;
	
	@OneToOne
	@JoinColumn(name = "reviewId")
	private FilmReview review;
	
	//Constructors
	public FilmPhoto() {}
	
	public FilmPhoto(String imageUrl, FilmReview review) {
		this.imageUrl = imageUrl;
		this.review = review;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public FilmReview getReview() {
		return review;
	}

	public void setReview(FilmReview review) {
		this.review = review;
	}

}
