package com.filmreview.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class FilmReview {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;
	
	private String review;
	private Short rating;
	
	//Relation with User entity
	@ManyToOne
	@JoinColumn(name = "userId")
	private User userId;
	
	//Relation with Film entity
	@ManyToOne
	@JoinColumn(name = "filmId")
	private Film filmId;
	
	//Relation with Comment entity
	@OneToMany(mappedBy = "reviewId")
	private List<Comment> comments;
	
	//Constructors
	public FilmReview() {}
	
	public FilmReview(String review, short rating) {
		this.review = review;
		this.rating = rating;
	}

	//Getters and Setters
	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public short getRating() {
		return rating;
	}

	public void setRating(Short rating) {
		this.rating = rating;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Film getFilmId() {
		return filmId;
	}

	public void setFilmId(Film filmId) {
		this.filmId = filmId;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
