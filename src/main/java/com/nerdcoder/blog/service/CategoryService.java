package com.nerdcoder.blog.service;

import java.util.List;

import com.nerdcoder.blog.payload.CategoryDto;

public interface CategoryService {

	CategoryDto addCategory(CategoryDto categoryDto);
	
	CategoryDto getCategory(Long categoryId);
	
	List<CategoryDto> getAllCategories();
	
	CategoryDto updateCategory(CategoryDto categoryDto, Long id);
	
	void deleteCategoryById(Long id);
}
