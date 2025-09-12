package com.Anshu.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.Anshu.dto.CategoryDto;
import com.Anshu.exception.ValidationException;

@Component
public class Validation {
	public void categoryValidation(CategoryDto categoryDto) {

		Map<String, Object> error = new LinkedHashMap<>();

		if (ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("category object/JSON should not be null or empty");
		} else {
			// validation name field
			if (ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "name field is empty or null");

			} else {
				if (categoryDto.getName().length() < 3) {
					error.put("name", "name field minimum 3");
				}
				if (categoryDto.getName().length() > 100) {
					error.put("name", "name field maximum 100");
				}

			}
		}

			// validation description field
			if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
				error.put("description", "description field is empty or null");

			}

			// validation isActive field
			if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
				error.put("isActive", "isActive field is empty or null");

			} else {
				if (categoryDto.getIsActive() != Boolean.TRUE.booleanValue() && categoryDto.getIsActive() != Boolean.FALSE.booleanValue()) {
					error.put("isActive", "invalid value isActive field");
				}

			}
			if(!error.isEmpty()) {
				throw new ValidationException(error);
			}

		}


}

