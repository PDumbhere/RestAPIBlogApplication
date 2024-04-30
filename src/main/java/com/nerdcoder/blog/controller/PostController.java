package com.nerdcoder.blog.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nerdcoder.blog.payload.PostDto;
import com.nerdcoder.blog.payload.PostDtoV2;
import com.nerdcoder.blog.payload.PostResponse;
import com.nerdcoder.blog.service.PostService;
import com.nerdcoder.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "CRUD REST APIs for Post Resource")
public class PostController {

	PostService postService;

	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}

	// create blog post
	@Operation(summary = "Create Post REST API", description = "Create Post REST API is used to save post to database")
	@ApiResponse(responseCode = "201", description = "HTTP Status 201 CREATED")
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/api/v1/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}

//	@GetMapping
//	public List<PostDto> getAllPosts(){
//		return postService.getAllPosts();
//	}

//	@GetMapping
//	public List<PostDto> getAllPosts(
//			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
//			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
//			){
//		return postService.getAllPosts(pageNo,pageSize);
//	}

	@GetMapping("/api/v1/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		PostResponse postResponse = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	// ------Example of versioning through URI path
//	@GetMapping("/api/v1/posts/{id}") 

	// ---Example of versioning through query parameter
//	@GetMapping(value = "/api/posts/{id}", params = "version=1") 

	// ---Example of versioning through custom headers
//	@GetMapping(value = "/api/posts/{id}", headers = "X-API-VERSION=1") 

	// -- Example of versioning through content negiotation
	@GetMapping(value = "/api/posts/{id}", produces = "application/vnd.nerdcoder.v1+json")
	public ResponseEntity<PostDto> getPostByIdV1(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	// ------Example of versioning through URI path
//	@GetMapping("/api/v2/posts/{id}")  

	// ---Example of versioning through query parameter
//	@GetMapping(value = "/api/posts/{id}", params = "version=2") 

	// ---Example of versioning through custom headers
//	@GetMapping(value = "/api/posts/{id}", headers = "X-API-VERSION=2") 

	// -- Example of versioning through content negiotation
	@GetMapping(value = "/api/posts/{id}", produces = "application/vnd.nerdcoder.v2+json")
	public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable(name = "id") long id) {
		PostDto postDto = postService.getPostById(id);
		PostDtoV2 postDtoV2 = new PostDtoV2(postDto.getId(), postDto.getTitle(), postDto.getDescription(),
				postDto.getContent(), postDto.getComments(), postDto.getCategoryId(), null);
		List<String> tags = new ArrayList<>();
		tags.add("Java");
		tags.add("Spring Boot");
		tags.add("AWS");
		postDtoV2.setTags(tags);

		return ResponseEntity.ok(postDtoV2);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/api/v1/posts/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
		PostDto responsePost = postService.updatePost(postDto, id);

		return ResponseEntity.ok(responsePost);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/api/v1/posts/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {

		postService.deletePostById(id);

		return new ResponseEntity<>("Post entity deleted successfully", HttpStatus.OK);
	}

	// Build Get Posts by Category Rest API
	@GetMapping("/api/v1/posts/category/{id}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(name = "id") Long categoryId) {
		List<PostDto> postDtos = postService.getPostByCategory(categoryId);
		return ResponseEntity.ok(postDtos);
	}

}
