package com.akhil.blog.services;

import com.akhil.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);
	
	void deleteComment(Integer commentId);

}
