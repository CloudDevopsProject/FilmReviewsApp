package com.filmreview.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;
	
	private String comment;
	
	//Relation with the User entity
	@ManyToOne
	@JoinColumn(name = "userId")
	private User userId;
	
	//Relation with the FilmReview entity
	@ManyToOne
	@JoinColumn(name = "reviewId")
	private FilmReview reviewId;
	
	//Getters and Setters
	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public FilmReview getReviewId() {
		return reviewId;
	}

	public void setReviewId(FilmReview reviewId) {
		this.reviewId = reviewId;
	}
}
