package com.filmreview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmreview.models.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long>{

}
