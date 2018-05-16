package com.edu.csuf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Course;
import com.edu.csuf.app.model.Semester;
import com.edu.csuf.app.model.User;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer>{
	List<Assignment> findByCourse(Course a);
	List<Assignment> findByCourseAndSemAndYear(Course c, Semester s, int year);
	Assignment findByAssignmentId(int id);
	
}
