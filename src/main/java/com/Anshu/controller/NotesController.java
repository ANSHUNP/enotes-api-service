package com.Anshu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Anshu.dto.NotesDto;
import com.Anshu.service.NotesService;
import com.Anshu.util.CommonUtil;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

	@Autowired
	private NotesService notesService;

	@PostMapping("/save")
	public ResponseEntity<?> saveNotes(@RequestBody NotesDto notesDto) {
		Boolean saveNotes = notesService.saveNotes(notesDto);
		if (saveNotes) {
			return CommonUtil.createBuildResponseMessage("notes save successfully", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMESSAGE("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/")
	public ResponseEntity<?> getAllNotes() {
		List<NotesDto> notes = notesService.getAllNotes();
		if (CollectionUtils.isEmpty(notes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
	}

}
