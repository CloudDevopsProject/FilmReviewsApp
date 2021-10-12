// This class is the entity used to set whether an application user is a admin or generic user

package com.filmreview.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private short roleID;
	
	private String role;
	
	//Setters and Getters
	public short getRoleID() {
		return roleID;
	}

	public void setRoleID(short roleID) {
		this.roleID = roleID;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	} 
}
