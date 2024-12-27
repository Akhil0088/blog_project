package com.akhil.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akhil.blog.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
