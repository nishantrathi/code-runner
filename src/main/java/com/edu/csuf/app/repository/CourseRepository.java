package com.edu.csuf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.csuf.app.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{
	List<Course> findByCourseIdIn(List<Integer> courseList);
}
