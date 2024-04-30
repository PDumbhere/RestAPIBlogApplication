package com.nerdcoder.blog.service;

import java.util.List;

import com.nerdcoder.blog.entity.Post;
import com.nerdcoder.blog.payload.PostDto;
import com.nerdcoder.blog.payload.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto);
	
//	List<PostDto> getAllPosts();
//	List<PostDto> getAllPosts(int pageNo, int pageSize);
	PostResponse getAllPosts(int pageNo, int pageSize,String sortBy, String sortDir);

	
	PostDto getPostById(long id);
	
	PostDto updatePost(PostDto postDto, long id);
	
	void deletePostById(long id);
	
	List<PostDto> getPostByCategory(Long categoryId);

}
