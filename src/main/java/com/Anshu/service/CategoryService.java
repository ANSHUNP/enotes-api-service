package com.Anshu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Anshu.dto.CategoryDto;
import com.Anshu.dto.CategoryResponse;
import com.Anshu.entity.Category;

@Service
public interface CategoryService {

	// saving categoryDto
	public Boolean saveCategory(CategoryDto categoryDto);

	// getting category
	public List<CategoryDto> getAllCategory();
	
	//getting active category
	public List<CategoryResponse> getActiveCategory();

}
