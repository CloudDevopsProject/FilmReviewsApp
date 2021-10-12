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
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	//Relation with UserRole entity
	@ManyToOne
	@JoinColumn(name = "roleId")
	private UserRole roleId;
	
	//Relation with FilmReview entity
	@OneToMany(mappedBy = "userId")
	private List<FilmReview> reviews;
	
	//Relation with Comment entity
	@OneToMany(mappedBy = "userId")
	private List<Comment> comments;
	
	//Getters and Setters
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRoleId() {
		return roleId;
	}

	public void setRoleId(UserRole roleId) {
		this.roleId = roleId;
	}

	public List<FilmReview> getReviews() {
		return reviews;
	}

	public void setReviews(List<FilmReview> reviews) {
		this.reviews = reviews;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
