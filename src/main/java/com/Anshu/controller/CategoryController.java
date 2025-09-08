package com.Anshu.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Anshu.dto.CategoryDto;
import com.Anshu.dto.CategoryResponse;
import com.Anshu.service.CategoryService;

@RestController
@RequestMapping("/api/V1/category")

public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/save")
	public ResponseEntity<?> savceCategory(@RequestBody CategoryDto categoryDto) {
		Boolean saveCategory = categoryService.saveCategory(categoryDto);

		if (saveCategory) {
			return new ResponseEntity<>("saved successfully 👍", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(" Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/")
	public ResponseEntity<?> getAllCategory() {
		List<CategoryDto> allCategory = categoryService.getAllCategory();

		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}
	}

	@GetMapping("/active")
	public ResponseEntity<?> getActiveCategory() {
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();

		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
		CategoryDto categoryDto = categoryService.getCategoryById(id);

		if (ObjectUtils.isEmpty(categoryDto)) {
			return new ResponseEntity<>("category not found with id =" + id, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(categoryDto, HttpStatus.OK);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
		Boolean deleted = categoryService.deleteCategory(id);

		if (deleted) {
			return new ResponseEntity<>("category deleted successfully = " + id, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>("Categor not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
