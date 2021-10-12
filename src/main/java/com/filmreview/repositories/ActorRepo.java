package com.filmreview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.models.Actor;

public interface ActorRepo extends JpaRepository<Actor, Short>{

}
