package com.Anshu.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Anshu.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer> {


List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);


Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId, PageRequest pageable);
			
	

	
}
