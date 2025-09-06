package com.Anshu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Anshu.dto.CategoryDto;
import com.Anshu.dto.CategoryResponse;
import com.Anshu.entity.Category;
import com.Anshu.service.CategoryService;

@RestController
@RequestMapping("/api/V1/category")

public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/save-category")
	public ResponseEntity<?> savceCategory(@RequestBody CategoryDto categoryDto) {
		Boolean saveCategory = categoryService.saveCategory(categoryDto);

		if (saveCategory) {
			return new ResponseEntity<>("save", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(" Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/category")
	public ResponseEntity<?> getAllCategory() {
		List<CategoryDto> allCategory = categoryService.getAllCategory();

		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}
	}

	@GetMapping("/active-category")
	public ResponseEntity<?> getActiveCategory() {
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();

		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}

	}
}
