package com.devskiller.tasks.blog.rest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerTest extends AbstractControllerTest {


	@Test
	public void shouldReturnFoundComments() throws Exception {

		// given
		List<CommentDto> comments = new ArrayList<>();
		LocalDateTime creationDate = LocalDateTime.of(2018, 5, 20, 20, 51, 16);
		comments.add(new CommentDto(2L, "comment content", "John Smith", creationDate));

		// when
		when(commentService.getCommentsForPost(1L)).thenReturn(comments);

		// then
		mockMvc.perform(get("/posts/1/comments").accept(APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].id", is(2)))
			.andExpect(jsonPath("$[0].comment", is("comment content")))
			.andExpect(jsonPath("$[0].author", is("John Smith")))
			.andExpect(jsonPath("$[0].creationDate", is(creationDate.toString())));

	}

	@Test
	public void shouldAddComment() throws Exception {

		// given
		String commentBody = "{\"content\":\"Test content\", \"author\":\"John Doe\"}";
		NewCommentDto newComment = createComment("Test content", "John Doe");

		// when
		when(commentService.addComment(any(), eq(newComment))).thenReturn(1L);

		// then
		mockMvc.perform(post("/posts/1/comments")
				.content(commentBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
			.andExpect(status().isCreated());
	}

	private NewCommentDto createComment(String content, String author) {
		return new NewCommentDto(author, content);
	}

}
