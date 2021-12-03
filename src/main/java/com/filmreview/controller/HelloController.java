package com.filmreview.controller;

import org.springframework.web.bind.annotation.*;


public class HelloController {

	
	
	public String hello(String name) {
		
		return "Hello, "+name;		
	}
	
}
