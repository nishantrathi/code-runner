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
@Table(name="studentCourseSemMapping")
public class StudentCourseSemMap {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="scsId")
	private long scsId;

	@Valid
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
	
	@Column(name="year")
	private int year;
	
	public long getScsId() {
		return scsId;
	}
	public void setScsId(long scsId) {
		this.scsId = scsId;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
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

}
