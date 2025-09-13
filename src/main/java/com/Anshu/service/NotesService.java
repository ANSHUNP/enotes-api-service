package com.Anshu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Anshu.dto.NotesDto;
import com.Anshu.exception.ResourceNotFoundException;

@Service
public interface NotesService {

	// saving NotesDto
	public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException;

	// getting all notes
	public List<NotesDto> getAllNotes();
}
