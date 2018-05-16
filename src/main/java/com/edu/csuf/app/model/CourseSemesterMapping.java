package com.edu.csuf.app.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Table(name="courseSemesterMapping")
public class CourseSemesterMapping {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="")
	private int courseSemId;

	@Column(name="")
	private int year;
	
	public Semester getSem() {
		return sem;
	}

	public void setSem(Semester sem) {
		this.sem = sem;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Valid
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE})
	@JoinColumn(name="semesterId")
	private Semester sem;
	
	@Valid
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE})
	@JoinColumn(name="courseId")
	private Course course;

	public int getCourseSemId() {
		return courseSemId;
	}

	public void setCourseSemId(int courseSemId) {
		this.courseSemId = courseSemId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
