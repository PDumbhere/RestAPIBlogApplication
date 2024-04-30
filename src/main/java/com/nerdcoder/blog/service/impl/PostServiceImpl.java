package com.nerdcoder.blog.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.mapping.Collection;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nerdcoder.blog.entity.Category;
import com.nerdcoder.blog.entity.Comment;
import com.nerdcoder.blog.entity.Post;
import com.nerdcoder.blog.exception.ResourceNotFoundException;
import com.nerdcoder.blog.payload.CommentDto;
import com.nerdcoder.blog.payload.PostDto;
import com.nerdcoder.blog.payload.PostResponse;
import com.nerdcoder.blog.repository.CategoryRepository;
import com.nerdcoder.blog.repository.PostRepository;
import com.nerdcoder.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	
	private CategoryRepository categoryRepository;

//	private ModelMapper modelMapper;
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository , CategoryRepository categoryRepository
//			,ModelMapper modelMapper
			) {
		this.postRepository = postRepository;
		this.categoryRepository = categoryRepository;
//		this.modelMapper = modelMapper;
	}
	
	@Override
	public PostDto createPost(PostDto postDto) {
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", postDto.getCategoryId()));
		
		Post post = mapToEntity(postDto);
		post.setCategory(category);
		Post newPost = postRepository.save(post);
		
		PostDto postResponse = mapToDto(newPost);
		return postResponse;
	}
	
//	@Override
//	public List<PostDto> getAllPosts() {
//		List<Post> posts= postRepository.findAll();
//		
//		List<PostDto> postsDto = posts.stream()
////				.map(post -> mapToDto(post)).collect(Collectors.toList());
//					.map(post -> mapToDto(post)).toList();
//		
//		
//		return postsDto;
//	}
	
//	@Override
//	public List<PostDto> getAllPosts(int pageNo, int pageSize) {
//		
//		Pageable pageable = PageRequest.of(pageNo, pageSize);
//		
//		Page<Post> posts= postRepository.findAll(pageable);
//		
//		List<Post> listOfPost = posts.getContent();
//		
//		List<PostDto> postsDto = listOfPost.stream()
////				.map(post -> mapToDto(post)).collect(Collectors.toList());
//					.map(post -> mapToDto(post)).toList();
//		
//		
//		return postsDto;
//	}
	
	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
													? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();		
		Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
		
		Page<Post> posts= postRepository.findAll(pageable);
		
		List<Post> listOfPost = posts.getContent();
		
		List<PostDto> postsDto = listOfPost.stream()
//				.map(post -> mapToDto(post)).collect(Collectors.toList());
					.map(post -> mapToDto(post)).toList();
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postsDto);
		postResponse.setPageNo(posts.getNumber()+1);
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElement(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		
		return postResponse;
	}
	
	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
		return mapToDto(post);
	}
	
	
	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", postDto.getCategoryId()));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCategory(category);
		
		Post updatedPost = postRepository.save(post);
		
		return mapToDto(updatedPost);
	}
	

	@Override
	public void deletePostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
		
		postRepository.delete(post);
	}

	@Override
	public List<PostDto> getPostByCategory(Long categoryId) {
		
		Category category = categoryRepository.findById(categoryId)
							.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		
		List<Post> posts = postRepository.findByCategoryId(categoryId);
		
		return posts.stream().map(post -> mapToDto(post))
				.collect(Collectors.toList());
	}

	// convert Entity to Dto
	private PostDto mapToDto(Post post) {
		
//		PostDto postDto = modelMapper.map(post, PostDto.class);
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());
		
		Set<CommentDto> comments = post.getComments().stream()
				.map(comment -> mapToDto(comment)).collect(Collectors.toSet());
		
		postDto.setComments(comments);
		postDto.setCategoryId(post.getCategory().getId());
		
		return postDto;
	}
	
	
	// convert Dto to Entity
	private Post mapToEntity(PostDto postDto) {
		
//		Post post = modelMapper.map(postDto, Post.class);
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		return post;
	}
	
	private CommentDto mapToDto(Comment comment) {
//		CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
		CommentDto commentDto = new CommentDto();
		commentDto.setId(comment.getId());
		commentDto.setName(comment.getName());
		commentDto.setEmail(comment.getEmail());
		commentDto.setBody(comment.getBody());

		return commentDto;
	}



}
