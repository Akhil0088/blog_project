package com.akhil.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akhil.blog.payloads.ApiResponse;
import com.akhil.blog.payloads.CategoryDto;
import com.akhil.blog.payloads.UserDto;
import com.akhil.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	// API for creating a Category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createcategory(@Valid @RequestBody CategoryDto categoryDto){
		
		CategoryDto createdCategory = this.categoryService.createCategoryDto(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategory,HttpStatus.CREATED);
	}
	
	// API for updating a category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid  @RequestBody CategoryDto categoryDto , @PathVariable Integer categoryId){
		CategoryDto updatedCategory = this.categoryService.updateCategoryDto(categoryDto, categoryId);
		return ResponseEntity.ok(updatedCategory);
	}
	
	// API for deleting a Category
	@DeleteMapping("/{categoryId}")
	 public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId)
	 {
		 this.categoryService.deleteCategoryDto(categoryId);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted Successfully", true),HttpStatus.OK);
	 }
	
	// Api for getting a All category
	@GetMapping("/")
	 public ResponseEntity<List<CategoryDto>> getAllCategory(){
		
		 return ResponseEntity.ok(this.categoryService.getAllCategory());
	 }
	
	
	@GetMapping("/{categoryId}")
	 public ResponseEntity<CategoryDto> getAllUsers(@PathVariable Integer categoryId){ 
		 return ResponseEntity.ok(this.categoryService.getcategoryById(categoryId));
	 }
	
	
	

}
