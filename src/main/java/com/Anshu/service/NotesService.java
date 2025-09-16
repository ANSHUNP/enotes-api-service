package com.Anshu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Anshu.dto.NotesDto;


@Service
public interface NotesService {

	// saving NotesDto
	public Boolean saveNotes(String notes,MultipartFile file) throws Exception;

	// getting all notes
	public List<NotesDto> getAllNotes();
}
