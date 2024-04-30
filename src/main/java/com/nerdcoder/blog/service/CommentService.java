package com.nerdcoder.blog.service;

import java.util.List;

import com.nerdcoder.blog.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto> getCommentsByPostId(long postId);
	
	CommentDto getCommentById(long postId, long Id);
	
	CommentDto updateComment(long postId, long id, CommentDto commentDto);
	
	void deleteComment(long postId, long id);
}
