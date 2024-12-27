package com.akhil.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.akhil.blog.payloads.CategoryDto;

@Service
public interface CategoryService {

	
	
	// Create Category
	 CategoryDto createCategoryDto(CategoryDto categoryDto);
	
	
	// Update Category
	 CategoryDto updateCategoryDto(CategoryDto categoryDto, Integer categoryId);
	
	// Delete category
	 void deleteCategoryDto(Integer categoryId);
	
	// Get Category By Id
	 CategoryDto getcategoryById(Integer categoryId);
	 
	 //Get all category
	 List<CategoryDto> getAllCategory();
	
	
	
}
