package com.akhil.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akhil.blog.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

	
	
}