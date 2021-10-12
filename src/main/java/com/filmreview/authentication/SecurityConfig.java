package com.filmreview.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public AppUserDetailsService appUserDetailsService() {
		return new AppUserDetailsService();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Instantiate a DaoAuthenticationProvider instance
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		//Instantiate a BCryptPasswordEncoder instance
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//Set the DaoAuthentication Instances values
		authProvider.setUserDetailsService(appUserDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder);
		auth.authenticationProvider(authProvider);	
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http
		 	.authorizeRequests()
		 	.and()
		 		.formLogin()
			 		.loginPage("/login")
		     		.loginProcessingUrl("/login")
		     		.usernameParameter("email")
		     		.defaultSuccessUrl("/", true)
		     		.failureUrl("/loginFailure")
		     		.permitAll()   
	     	.and()
		     	.logout()
	     			.logoutUrl("/logout")
	     			.logoutSuccessUrl("/")
	     			.permitAll();   
	}
	
	 @Override
	 public void configure(WebSecurity web) {
		 web.ignoring()
	     	.antMatchers("/resources/**", "/static/**", "/login");
	 }
	
	
	
	

}
