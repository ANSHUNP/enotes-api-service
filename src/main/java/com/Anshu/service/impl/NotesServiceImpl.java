package com.Anshu.service.impl;

import java.io.File;
import java.io.FileInputStream;
import org.springframework.util.StreamUtils;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.Anshu.dto.NotesDto;
import com.Anshu.dto.NotesDto.CategoryDto;
import com.Anshu.dto.NotesResponse;
import com.Anshu.entity.FileDetails;
import com.Anshu.entity.Notes;
import com.Anshu.exception.ResourceNotFoundException;
import com.Anshu.repository.CategoryRepository;
import com.Anshu.repository.FileRepository;
import com.Anshu.repository.NotesRepository;
import com.Anshu.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository notesRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private FileRepository fileRepo;

	@Value("${file.upload.path}")

	private String uploadpath;

	private File fileDtls;

	@Override
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

		// converting string to json and map with dto
		ObjectMapper ob = new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class);
		notesDto.setIsDeleted(false);
		notesDto.setDeletedOn(null);

		// updating notes
		// Integer id = notesDto.getId();
		if (!ObjectUtils.isEmpty(notesDto.getId())) {
			updateNotes(notesDto, file);
		}

		// category validation
		checkCategoryExist(notesDto.getCategory());

		Notes notesMap = mapper.map(notesDto, Notes.class);

		FileDetails fileDtls = saveFileDetails(file);

		if (!ObjectUtils.isEmpty(fileDtls)) {
			notesMap.setFileDetails(fileDtls);
		} else {
			if (ObjectUtils.isEmpty(notesDto.getId()))
				notesMap.setFileDetails(null);
		}

		Notes saveNotes = notesRepo.save(notesMap);
		if (!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}

		return false;
	}

	// finding id for updating notes
	private void updateNotes(NotesDto notesDto, MultipartFile file) throws Exception {
		Notes existNotes = notesRepo.findById(notesDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("invalid notes id"));

	}

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {

		if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {

			String originalFilename = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFilename);

			List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpg", "jpeg");
			if (!extensionAllow.contains(extension)) {
				throw new IllegalArgumentException("invalid file format! Upload only .pdf,.xlsx,.jpg only");
			}

			String rndString = UUID.randomUUID().toString();
			String uploadfileName = rndString + "." + extension;

			File saveFile = new File(uploadpath);
			if (!saveFile.exists())

			{
				saveFile.mkdirs();
			}

			// path:enotesapiservice/notes/java.pdf
			String storePath = uploadpath.concat(uploadfileName);

			// upload file
			Long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			if (upload != 0) {
				FileDetails fileDtls = new FileDetails();
				fileDtls.setOriginalFileName(originalFilename);
				fileDtls.setDisplayFileName(getDisplayName(originalFilename));
				fileDtls.setUploadFileName(uploadfileName);
				fileDtls.setFileSize(file.getSize());
				fileDtls.setPath(storePath);
				FileDetails saveFileDtls = fileRepo.save(fileDtls);
				return saveFileDtls;
			}

		}

		return null;
	}

	private String getDisplayName(String originalFilename) {
		// java_programming_tutorials.pdf
		// what will return==java_prog.pdf
		String extension = FilenameUtils.getExtension(originalFilename);
		String fileName = FilenameUtils.removeExtension(originalFilename);

		if (fileName.length() > 8) {
			fileName = fileName.substring(0, 7);
		}
		fileName = fileName + "." + extension;
		return fileName;
	}

	private void checkCategoryExist(CategoryDto category) throws Exception {
		categoryRepo.findById(category.getId())
				.orElseThrow(() -> new ResourceNotFoundException("category id is invalid"));

	}

	@Override
	public List<NotesDto> getAllNotes() {
		return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();

	}

	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		FileDetails fileDtls = fileRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("file is not available"));

		return fileDtls;
	}

	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {

		InputStream io = new FileInputStream(fileDetails.getPath());

		return StreamUtils.copyToByteArray(io);
	}

	@Override
	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {

		PageRequest pageable = PageRequest.of(pageNo, pageSize);
		Page<Notes> pageNotes = notesRepo.findByCreatedByAndIsDeletedFalse(userId, pageable);

		List<NotesDto> notesDto = pageNotes.get().map(n -> mapper.map(n, NotesDto.class)).toList();

		NotesResponse notes = NotesResponse.builder().notes(notesDto).pageNo(pageNotes.getNumber())
				.pageSize(pageNotes.getSize()).totalElements(pageNotes.getTotalElements())
				.totalPages(pageNotes.getTotalPages()).isFirst(pageNotes.isFirst()).isLast(pageNotes.isLast()).build();

		return notes;

	}

	@Override
	public void softDeleteNotes(Integer id) throws Exception {
		Notes notes = notesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notes id is invalid"));
		notes.setIsDeleted(true);
		notes.setDeletedOn(new Date());
		notesRepo.save(notes);

	}

	@Override
	public void restoreNotes(Integer id) throws ResourceNotFoundException {
		Notes notes = notesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notes id is invalid"));
		notes.setIsDeleted(false);
		notes.setDeletedOn(null);
		notesRepo.save(notes);

	}

	@Override
	public List<NotesDto> getUserRecycleBinNotes(Integer userId) {
		List<Notes> recycleNotes = notesRepo.findByCreatedByAndIsDeletedTrue(userId);

		List<NotesDto> notesDtoList = recycleNotes.stream().map(note -> mapper.map(note, NotesDto.class)).toList();

		return notesDtoList;
	}

}
