package com.edu.csuf.app.repository;

import java.util.List;

import com.edu.csuf.app.model.User;

public interface CustomUserRepository {
	public List<User> findByFirstNameLastNameCwid(User user);
	
	public List<Integer> getRegisteredCoursesForStudent(long studentId, long semId, int year);
}
