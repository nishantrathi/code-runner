package com.edu.csuf.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.csuf.app.model.Semester;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
	public Semester findByActive(int status);
}
