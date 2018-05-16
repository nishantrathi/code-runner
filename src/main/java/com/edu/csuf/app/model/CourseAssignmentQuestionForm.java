package com.edu.csuf.app.model;

import javax.validation.Valid;

public class CourseAssignmentQuestionForm {
	
	@Valid
	private Course course;
	
	@Valid
	private Assignment assignment;
	
	@Valid 
	private Question ques;
	
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public Question getQues() {
		return ques;
	}

	public void setQues(Question ques) {
		this.ques = ques;
	}

}
