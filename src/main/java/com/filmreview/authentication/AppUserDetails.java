//This class is used to allow user's to login to the application using their email address

package com.filmreview.authentication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.filmreview.models.User;

public class AppUserDetails implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Instantiate a User instance
	User user;
	
	public AppUserDetails(User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}
	
	//This method will allow users to log in using their email address instead of a username
	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//This method will return the role id value for whatever user is logged into the application
	public short getRoleId() {
		return user.getRoleId().getRoleID();
	}

}
