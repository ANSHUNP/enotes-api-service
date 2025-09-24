package com.Anshu.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class NotesDto {

	private Integer id;

	private String title;

	private String description;

	private CategoryDto category;

	private Integer createdBy;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;
	
	private FilesDto fileDetails;
	
	private boolean isDeleted;
	
	private Date deletedOn;

	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CategoryDto {
		private Integer id;
		private String name;
	}
	
	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FilesDto {
		private Integer id;

		private String uploadFileName;

		private String originalFilename;

		private String displayFilename;

	
	}

	public void setIsDeleted(boolean b) {
		// TODO Auto-generated method stub
		
	}

}
