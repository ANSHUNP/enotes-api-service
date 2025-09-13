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
import com.Anshu.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@RestController
@RequestMapping("/api/v1/category")

public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/save")
	public ResponseEntity<?> savceCategory(@RequestBody CategoryDto categoryDto) {
		Boolean saveCategory = categoryService.saveCategory(categoryDto);

		if (saveCategory) {
			return CommonUtil.createBuildResponseMessage("saved successfully 👍", HttpStatus.CREATED);
//			return new ResponseEntity<>("saved successfully 👍", HttpStatus.CREATED);
		} else {
			return CommonUtil.createErrorResponseMESSAGE("not saved ", HttpStatus.INTERNAL_SERVER_ERROR);
//			return new ResponseEntity<>(" Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/")
	public ResponseEntity<?> getAllCategory() {
		List<CategoryDto> allCategory = categoryService.getAllCategory();

		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
//			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}
	}

	@GetMapping("/active")
	public ResponseEntity<?> getActiveCategory() {
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();

		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
//			return new ResponseEntity<>(allCategory, HttpStatus.OK);
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception {

		CategoryDto categoryDto = categoryService.getCategoryById(id);
		if (ObjectUtils.isEmpty(categoryDto)) {
//			return new ResponseEntity<>("Internal server error", HttpStatus.NOT_FOUND);
			return CommonUtil.createErrorResponseMESSAGE("Internal server error", HttpStatus.NOT_FOUND);
		}
//		return new ResponseEntity<>(categoryDto, HttpStatus.OK);
		return CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
		Boolean deleted = categoryService.deleteCategory(id);

		if (deleted) {
//			return new ResponseEntity<>("category deleted successfully ", HttpStatus.OK);
			return CommonUtil.createBuildResponse("category deleted successfully", HttpStatus.OK);

		} else {
//			return new ResponseEntity<>("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
			return CommonUtil.createErrorResponseMESSAGE("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
