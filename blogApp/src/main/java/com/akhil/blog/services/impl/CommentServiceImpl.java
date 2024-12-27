package com.akhil.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akhil.blog.entity.Comment;
import com.akhil.blog.entity.Posts;
import com.akhil.blog.entity.User;
import com.akhil.blog.exceptions.ResourceNotFoundException;
import com.akhil.blog.payloads.CommentDto;
import com.akhil.blog.repositories.CommentRepo;
import com.akhil.blog.repositories.PostRepo;
import com.akhil.blog.repositories.UserRepo;
import com.akhil.blog.services.CommentService;


@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		Posts post = this.postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("post", "postid", postId));
		
		User user = this.userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("user", "userid", userId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setContent(commentDto.getContent());
		comment.setPost(post);
		comment.setUser(user);
		
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).
				orElseThrow(()-> new ResourceNotFoundException("comment", "commentid", commentId));
		
		this.commentRepo.delete(comment);
		
	}

}
