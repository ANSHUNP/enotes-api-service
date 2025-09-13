package com.Anshu.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.Anshu.dto.NotesDto;
import com.Anshu.entity.Notes;
import com.Anshu.repository.NotesRepository;
import com.Anshu.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository notesRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean saveNotes(NotesDto notesDto) {

		// validation
		Notes notes = mapper.map(notesDto, Notes.class);
		Notes saveNotes = notesRepo.save(notes);
		if(!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}

		return false;
	}

	@Override
	public List<NotesDto> getAllNotes() {
      return notesRepo.findAll().stream()
    		  .map(note->mapper.map(note, NotesDto.class)).toList();
		
	}

}
