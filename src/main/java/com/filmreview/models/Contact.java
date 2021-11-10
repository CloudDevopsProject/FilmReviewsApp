package com.filmreview.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Contact")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cId;
	private String name;
	private String email;
	private String phone;
	
	@Column(length=1000)
	private String description;

	//Constructors
	public Contact() {}
	
	/*public Contact(String name, String email, String phone, String description) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.description = description;
	}*/
	
	
	
	
	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Override
	public String toString() {
		return "Contact [cId=" + cId + ", name=" + name + ", email=" + email + ", phone=" + phone + ", description="
				+ description + "]";
	}
	
}
