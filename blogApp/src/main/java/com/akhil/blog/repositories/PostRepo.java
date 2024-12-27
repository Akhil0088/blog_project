package com.akhil.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akhil.blog.entity.Category;
import com.akhil.blog.entity.Posts;
import com.akhil.blog.entity.User;

public interface PostRepo extends JpaRepository<Posts, Integer> {

	List<Posts> findByCategory(Category category);
	
	List<Posts> findByUser(User user);
	
	List<Posts> findByTitleContaining(String title);
}
