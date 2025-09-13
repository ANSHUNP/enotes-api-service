package com.Anshu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Anshu.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

	
}
