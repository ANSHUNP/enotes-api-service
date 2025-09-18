package com.Anshu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Anshu.dto.NotesDto;
import com.Anshu.dto.NotesResponse;
import com.Anshu.entity.FileDetails;


@Service
public interface NotesService {

	// saving NotesDto
	public Boolean saveNotes(String notes,MultipartFile file) throws Exception;

	// getting all notes
	public List<NotesDto> getAllNotes();

	//downloading file 
	public byte[] downloadFile(FileDetails fileDetails) throws Exception;

	public FileDetails getFileDetails(Integer id) throws Exception;

	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);


	
}
