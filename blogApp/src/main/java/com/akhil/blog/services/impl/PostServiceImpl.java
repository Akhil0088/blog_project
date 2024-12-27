package com.akhil.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.akhil.blog.entity.Category;
import com.akhil.blog.entity.Posts;
import com.akhil.blog.entity.User;
import com.akhil.blog.exceptions.ResourceNotFoundException;
import com.akhil.blog.payloads.PostDto;
import com.akhil.blog.payloads.PostResponse;
import com.akhil.blog.repositories.CategoryRepo;
import com.akhil.blog.repositories.PostRepo;
import com.akhil.blog.repositories.UserRepo;
import com.akhil.blog.services.PostService;


@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryrepo;
	
	
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		
		Category cat = this.categoryrepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		
		Posts post = this.modelMapper.map(postDto, Posts.class);
		post.setUser(user);
		post.setCategory(cat);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		
		Posts newPost = this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer id) {
		Posts post = this.postRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id) );
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Posts updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer id) {
		Posts post = this.postRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id) );
		
		this.postRepo.delete(post);
		
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy) {
		
		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		
		 Page<Posts> pagePost =this.postRepo.findAll(p);
		 List<Posts>  posts = pagePost.getContent();
		List<PostDto> postDto = posts.stream()
				.map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse response = new PostResponse();
		response.setContent(postDto);
		response.setTotalPages(pagePost.getTotalPages());
		response.setPageNumber(pagePost.getNumber());
		response.setPageSize(pagePost.getSize());
		response.setTotalElements(pagePost.getTotalElements());
		response.setLastpage(pagePost.isLast());
		
		return response;
	}

	@Override
	public PostDto getPostById(Integer id) {
		// TODO Auto-generated method stub
		Posts post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id) );
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category cat = this.categoryrepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
		
		List<Posts> posts = this.postRepo.findByCategory(cat);
		
		List<PostDto> postDtos = posts.stream()
				.map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Id", userId));
		
		List<Posts> posts =  this.postRepo.findByUser(user);
		
		
		List<PostDto> postDtos = posts.stream()
				.map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
		
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Posts> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDto = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDto;
	}

}
