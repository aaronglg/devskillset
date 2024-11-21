package com.devskiller.tasks.blog.rest;

import com.devskiller.tasks.blog.model.Comment;
import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;
import com.devskiller.tasks.blog.model.dto.PostDto;
import com.devskiller.tasks.blog.service.CommentService;
import com.devskiller.tasks.blog.service.PostService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/posts")
public class CommentController {

	@Resource
	private  CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping(value = "/{id}/comments")
	@ResponseStatus(HttpStatus.OK)
	public List<CommentDto> getComnents(@PathVariable Long id) {
		return commentService.getCommentsForPost(id);
	}

	@PostMapping(value = "/{id}/comments")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<NewCommentDto> postComment(@PathVariable Long id, @RequestBody NewCommentDto newCommentDto) {
		Long result =  commentService.addComment(id,newCommentDto);
		if(null !=  result){
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}


}
