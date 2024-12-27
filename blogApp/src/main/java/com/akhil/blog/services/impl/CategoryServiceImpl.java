package com.akhil.blog.services.impl;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akhil.blog.entity.Category;
import com.akhil.blog.exceptions.ResourceNotFoundException;
import com.akhil.blog.payloads.CategoryDto;
import com.akhil.blog.repositories.CategoryRepo;
import com.akhil.blog.services.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {
	
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategoryDto(CategoryDto categoryDto) {
		Category cat = this.categoryDtoTocategory(categoryDto);
		System.out.println();
		Category savedCategory = this.categoryRepo.save(cat);
		return this.categoryTocategoryDto(savedCategory);
	}

	@Override
	public CategoryDto updateCategoryDto(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(
				() -> new ResourceNotFoundException("Category", "Id", categoryId));
		cat.setDescription(categoryDto.getDescription());
		cat.setTitle(categoryDto.getTitle());
		
		Category updatedCategory = this.categoryRepo.save(cat);
		
		return this.categoryTocategoryDto(updatedCategory);
	}

	@Override
	public void deleteCategoryDto(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(
				() -> new ResourceNotFoundException("Category", "Id", categoryId));
		
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getcategoryById(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(
				() -> new ResourceNotFoundException("Category", "Id", categoryId));
		
		return this.categoryTocategoryDto(cat);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = this.categoryRepo.findAll();
		
		List<CategoryDto> catDto = categories.stream().map((cat) -> this.categoryTocategoryDto(cat)).collect(Collectors.toList()); 
		return catDto;
	}
	
	
	public Category categoryDtoTocategory(CategoryDto categoryDto) {
		
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return category;
		
	}
	
	public CategoryDto categoryTocategoryDto(Category category) {
		CategoryDto cat = this.modelMapper.map(category, CategoryDto.class);
		
		return cat;
	}

}
