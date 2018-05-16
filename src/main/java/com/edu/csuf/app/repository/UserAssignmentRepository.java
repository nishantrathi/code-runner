package com.edu.csuf.app.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Course;
import com.edu.csuf.app.model.Semester;
import com.edu.csuf.app.model.User;
import com.edu.csuf.app.model.UserAssignmentMapping;

public interface UserAssignmentRepository extends JpaRepository<UserAssignmentMapping, Long> {
	public List<UserAssignmentMapping> findByUserAndCourseAndSemAndAssignmentAndYear(User u, Course c, Semester s, Assignment a, int year);
	public List<UserAssignmentMapping> findByUserAndCourseAndSemAndYear(User u, Course c, Semester s, int year);
	
	@Modifying
	@Query("update UserAssignmentMapping ua set ua.finalSubmittedDate = ?1 where ua.assignment = ?2")
	public void updateDateForAssignment(String date, Assignment assign);
}
