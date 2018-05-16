package com.edu.csuf.app.daoService;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.csuf.app.model.Course;
import com.edu.csuf.app.repository.CourseRepository;
import com.edu.csuf.app.utility.Utilities;

@Service
public class CourseDataAccess {
	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private Utilities utilites;
	
	@Transactional
	public Course insertCourseDetails(Course course){
		course.setCreatedOn(utilites.generateTodaysDate());
		courseRepo.save(course);
		return course;
	}
	
	@Transactional
	public HashMap<Integer, String> getAllCourseMap(){
		HashMap<Integer, String> courseMap = new HashMap<Integer, String>();
		for (Course course : courseRepo.findAll()) {
			courseMap.put(course.getCourseId(), course.getCourseCode());
		}
		return courseMap;
	}
	
	@Transactional
	public List<Course> getCourseName(List<Integer> ids){
		return courseRepo.findByCourseIdIn(ids);
	}
	
	
	
	
}
