package com.devskiller.tasks.blog.repository;

import com.devskiller.tasks.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devskiller.tasks.blog.model.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
