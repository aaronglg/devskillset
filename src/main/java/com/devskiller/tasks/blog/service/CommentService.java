package com.devskiller.tasks.blog.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import com.devskiller.tasks.blog.model.Comment;
import com.devskiller.tasks.blog.model.Post;
import com.devskiller.tasks.blog.model.dto.PostDto;
import com.devskiller.tasks.blog.repository.CommentRepository;
import com.devskiller.tasks.blog.repository.PostRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;

@Service
public class CommentService {


	@Resource
	private  CommentRepository commentRepository;

	@Resource
	private  PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    /**
	 * Returns a list of all comments for a blog post with passed id.
	 *
	 * @param postId id of the post
	 * @return list of comments sorted by creation date descending - most recent first
	 */
	public List<CommentDto> getCommentsForPost(Long postId) {

		Optional<Post> foundPost = postRepository.findById(postId);
		if(foundPost.isEmpty()){
			return null;
		} else {
			List<Comment> comments = foundPost.get().getComments();
			List<CommentDto> commentDtoList = new ArrayList<>();
			if (!comments.isEmpty()) {
				comments.forEach(comment -> {
					CommentDto commentDto = new CommentDto(comment.getId(), comment.getComment(), comment.getAuthor(), comment.getCreationDate());
					commentDtoList.add(commentDto);
				});
			}
            return commentDtoList.stream().sorted(Comparator
				.comparing(CommentDto::creationDate))
				.collect(Collectors.toList());
		}
	}

	/**
	 * Creates a new comment
	 *
	 * @param postId id of the post
	 * @param newCommentDto data of new comment
	 * @return id of the created comment
	 *
	 * @throws IllegalArgumentException if postId is null or there is no blog post for passed postId
	 */
	public Long addComment(Long postId, NewCommentDto newCommentDto) {

		Optional<Post> foundPost = postRepository.findById(postId);
		if(foundPost.isEmpty()){
			return null;
		}else{
			Comment comment =  new Comment();
			comment.setComment(newCommentDto.content());
			comment.setAuthor(newCommentDto.author());
			comment.setCreationDate(LocalDateTime.now());
			Post post = foundPost.get();
			post.getComments().add(comment);
			commentRepository.save(comment);
			postRepository.save(post);
			return comment.getId();
		}
	}
}
