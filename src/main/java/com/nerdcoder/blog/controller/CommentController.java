package com.nerdcoder.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nerdcoder.blog.entity.Comment;
import com.nerdcoder.blog.payload.CommentDto;
import com.nerdcoder.blog.service.CommentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/posts/{postId}/comments")
@Tag(
		name = "CRUD REST APIs for Comment Resource"
)
public class CommentController {

	CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@SecurityRequirement(
			name = "Bearer Authentication"
	)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId, 
			 											@Valid @RequestBody CommentDto commentDto){
		
		return new ResponseEntity<>(commentService.createComment(postId, commentDto),HttpStatus.CREATED);
	}
	
	@GetMapping
	public List<CommentDto> getCommentsByPostId(@PathVariable(name = "postId") long postId){
		
		return commentService.getCommentsByPostId(postId);
	}
	
	@GetMapping(path = "{id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "postId")long postId,
														@PathVariable(name = "id") long id){
		
		return ResponseEntity.ok(commentService.getCommentById(postId, id));
	}
	
	@SecurityRequirement(
			name = "Bearer Authentication"
	)
	@PutMapping(path = "{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "postId") long postId,
						@PathVariable(name = "id") long id, @Valid @RequestBody CommentDto commentDto){
		
		return ResponseEntity.ok(commentService.updateComment(postId, id, commentDto));
	}
	
	@SecurityRequirement(
			name = "Bearer Authentication"
	)
	@DeleteMapping(path = "{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCommentById(@PathVariable(name = "postId") long postId, 
							@PathVariable(name = "id") long id){
		
		commentService.deleteComment(postId, id);
		
		return new ResponseEntity<>("Comment successfully Deleted", HttpStatus.OK);
	}
	
}
