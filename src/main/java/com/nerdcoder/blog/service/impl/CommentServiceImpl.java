package com.nerdcoder.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nerdcoder.blog.entity.Comment;
import com.nerdcoder.blog.entity.Post;
import com.nerdcoder.blog.exception.BlogApiException;
import com.nerdcoder.blog.exception.ResourceNotFoundException;
import com.nerdcoder.blog.payload.CommentDto;
import com.nerdcoder.blog.repository.CommentRepository;
import com.nerdcoder.blog.repository.PostRepository;
import com.nerdcoder.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	
	private CommentRepository commentRepository;
	private PostRepository postRepository;
//	private ModelMapper modelMapper;

	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository
//			, ModelMapper modelMapper
			) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
//		this.modelMapper = modelMapper;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

		Comment comment = mapToEntity(commentDto);

		comment.setPost(post);

		Comment commentCreated = commentRepository.save(comment);
		return mapToDto(commentCreated);
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {

		List<Comment> comments = commentRepository.findByPostId(postId);

		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long postId, long id) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", id));

		if (comment.getPost().getId() != (post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
		}
		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", id));

		if (comment.getPost().getId() != (post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
		}
		
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		
		Comment commentResponse = commentRepository.save(comment);

		return mapToDto(commentResponse);
	}

	@Override
	public void deleteComment(long postId, long id) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", id));

		if (comment.getPost().getId() != (post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
		}
		
		commentRepository.deleteById(id);
		
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

	private Comment mapToEntity(CommentDto commentDto) {
//		Comment comment = modelMapper.map(commentDto, Comment.class);
		Comment comment = new Comment();
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());

		return comment;
	}

}
