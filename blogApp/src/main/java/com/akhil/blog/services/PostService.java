package com.akhil.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.akhil.blog.entity.Posts;
import com.akhil.blog.payloads.PostDto;
import com.akhil.blog.payloads.PostResponse;

@Service
public interface PostService {

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	PostDto updatePost(PostDto postDto, Integer id);
	
	void deletePost(Integer id);
	
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy);
	
	PostDto getPostById(Integer id);
	
	// get all posts by category
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	//get all posts by user
	
	List<PostDto> getPostByUser(Integer userId); 
	
	// search posts
	
	List<PostDto> searchPosts(String keyword);
}
