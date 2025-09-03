package com.Anshu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Anshu.entity.Category;

@Service
public interface CategoryService {

	// saving category
	public Boolean saveCategory(Category category);

	// getting category
	public List<Category> getAllCategory();

}
