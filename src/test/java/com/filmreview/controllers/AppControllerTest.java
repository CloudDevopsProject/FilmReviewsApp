 package com.filmreview.controllers;
  
 import static org.junit.jupiter.api.Assertions.*;
 import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.filmreview.controller.*;
 
 
 public class AppControllerTest{
	 
	 AppController controller=new AppController();
	 
	 
	@Test
	 @PostMapping("/process-contact")
	 void contact() {
		 RedirectAttributes attributes = null;
		 String status = "Your query has been sent";
		 attributes.addFlashAttribute("pageMessage", status);
		 String response= controller.processContact("Name", "email", "phone", "description", attributes);
		 assertEquals("redirect:/contact", response);
		 
	 }
	 
 }