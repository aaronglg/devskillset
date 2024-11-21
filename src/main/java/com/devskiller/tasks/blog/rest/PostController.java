package com.devskiller.tasks.blog.rest;

import com.devskiller.tasks.blog.model.dto.NewCommentDto;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.devskiller.tasks.blog.model.dto.PostDto;
import com.devskiller.tasks.blog.service.PostService;

@Controller
@RestController
@RequestMapping("/posts")
public class PostController {

	@Resource
	private  PostService postService;


	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PostDto getPost(@PathVariable Long id) {
		return postService.getPost(id);
	}
}
