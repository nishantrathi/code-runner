package com.edu.csuf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Course;
import com.edu.csuf.app.model.Semester;
import com.edu.csuf.app.model.StudentQuery;

public interface QueryRepository extends JpaRepository<StudentQuery, Long>{
	public List<StudentQuery> findBySemAndCourseAndAssignmentAndYear(Semester sem, Course cour, Assignment assign, int year);
}
