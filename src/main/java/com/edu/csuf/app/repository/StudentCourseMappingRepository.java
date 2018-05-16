package com.edu.csuf.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.csuf.app.model.Semester;
import com.edu.csuf.app.model.StudentCourseSemMap;
import com.edu.csuf.app.model.User;

public interface StudentCourseMappingRepository extends JpaRepository<StudentCourseSemMap, Long> {
	public List<StudentCourseSemMap> findByUserAndSemAndYear(User user, Semester sem, int year);

}
