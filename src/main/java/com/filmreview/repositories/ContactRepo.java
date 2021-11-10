package com.filmreview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.models.Contact;


public interface ContactRepo extends JpaRepository<Contact, Long> {

}
