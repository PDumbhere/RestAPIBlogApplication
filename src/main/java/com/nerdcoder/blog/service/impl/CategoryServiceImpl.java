package com.nerdcoder.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nerdcoder.blog.entity.Category;
import com.nerdcoder.blog.exception.ResourceNotFoundException;
import com.nerdcoder.blog.payload.CategoryDto;
import com.nerdcoder.blog.repository.CategoryRepository;
import com.nerdcoder.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}



	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category category = mapToEntity(categoryDto);
		Category savedCategory = categoryRepository.save(category);
		return mapToDto(savedCategory);
	}
	
	@Override
	public CategoryDto getCategory(Long categoryId) {
		
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		return mapToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		
		List<Category> categories = categoryRepository.findAll();
		
		List<CategoryDto> response = categories.stream()
												.map(category -> mapToDto(category))
												.collect(Collectors.toList());
		return response;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
		
		Category category = categoryRepository.findById(id).orElseThrow(
									() -> new ResourceNotFoundException("Category", "Id", id));
		
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		
		Category response = categoryRepository.save(category);
		return mapToDto(response);
	}
	
	@Override
	public void deleteCategoryById(Long id) {
		Category category = categoryRepository.findById(id).orElseThrow(
								() -> new ResourceNotFoundException("Category", "id", id));
		
		categoryRepository.delete(category);
	}



	private Category mapToEntity(CategoryDto categoryDto) {
		Category category = new Category();
//		category.setId(categoryDto.getId());
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		return category;
	}
	
	private CategoryDto mapToDto(Category category) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(category.getId());
		categoryDto.setName(category.getName());
		categoryDto.setDescription(category.getDescription());
		return categoryDto;
	}

}
