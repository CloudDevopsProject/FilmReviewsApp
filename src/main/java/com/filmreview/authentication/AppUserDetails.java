//This class is used to allow user's to login to the application using their email address

package com.filmreview.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.filmreview.models.User;

public class AppUserDetails implements UserDetails{
	
	//Instantiate a User instance
	private User user;
	
	public AppUserDetails(User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authority = new ArrayList<>();
		//Add user role using their roleId
		if(this.getRoleId() == 1) {
			authority.add(new SimpleGrantedAuthority("admin"));
		} else if (this.getRoleId() == 2) {
			authority.add(new SimpleGrantedAuthority("genericUser"));
		}
		return authority;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
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
	
	//This method will return the user id 
	public Long getUserId() {
		return user.getUserId();
	}
}
