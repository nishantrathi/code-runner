package com.edu.csuf.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Table(name="userAssignmentMapping")
public class UserAssignmentMapping {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="uaId")
	private long uaId;
	
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE})
	@JoinColumn(name="userId")
	private User user;
	
	@Valid
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE})
	@JoinColumn(name="courseId")
	private Course course;
	
	@Valid
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE})
	@JoinColumn(name="semesterId")
	private Semester sem;
	
	@Valid
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE})
	@JoinColumn(name="assignId")
	private Assignment assignment;
	
	@Column(name="year")
	private int year;
	
	@Column(name="finalSubmittedDate")
	private String finalSubmittedDate;
	
	public long getUaId() {
		return uaId;
	}

	public void setUaId(long uaId) {
		this.uaId = uaId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Semester getSem() {
		return sem;
	}

	public void setSem(Semester sem) {
		this.sem = sem;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getFinalSubmittedDate() {
		return finalSubmittedDate;
	}

	public void setFinalSubmittedDate(String finalSubmittedDate) {
		this.finalSubmittedDate = finalSubmittedDate;
	}

	
}
