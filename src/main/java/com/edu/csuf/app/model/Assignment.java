package com.edu.csuf.app.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="assignment")
public class Assignment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="assignmentId")
	private int assignmentId;
	
	@Column(name="assignmentNumber")
	private int assignmentNumber;
	
	@Column(name="year")
	private int year;
	
	@ManyToOne
	@JoinColumn(name="courseId")
	private Course course;
	
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="semesterId")
	private Semester sem;
	
	@Column(name="createdOn")
	private String createdOn;
	
	@Column(name="endDate")
	private String endDate;
	
	@OneToMany(mappedBy="assignment",cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private Set<UserAssignmentMapping> userAssignmentMapping;
	
	@OneToMany(mappedBy="assignment",cascade=CascadeType.DETACH)
	private Set<StudentQuery> query;
	
	public Set<StudentQuery> getQuery() {
		return query;
	}

	public void setQuery(Set<StudentQuery> query) {
		this.query = query;
	}

	public Set<UserAssignmentMapping> getUserAssignmentMapping() {
		return userAssignmentMapping;
	}

	public void setUserAssignmentMapping(Set<UserAssignmentMapping> userAssignmentMapping) {
		this.userAssignmentMapping = userAssignmentMapping;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public Semester getSem() {
		return sem;
	}

	public void setSem(Semester sem) {
		this.sem = sem;
	}

	//mappedBy name came from question class(assign variable)
	@OneToMany(mappedBy="assign",cascade=CascadeType.ALL)
	private Set<Question> questions;
	
	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public int getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}

	public int getAssignmentNumber() {
		return assignmentNumber;
	}

	public void setAssignmentNumber(int assignmentNumber) {
		this.assignmentNumber = assignmentNumber;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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
}
