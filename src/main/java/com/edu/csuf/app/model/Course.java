package com.edu.csuf.app.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="course")
public class Course {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="cId")
	private int courseId;
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	@Column(name="courseName")
	private String courseName;
	
	@Column(name="courseCode")
	private String courseCode;
	
	@Column(name="createdOn")
	private Date createdOn;
	
	@OneToMany(mappedBy="course",cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private Set<StudentCourseSemMap> studentCourseSemMapping;
	
	@OneToMany(mappedBy="course",cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private Set<CourseSemesterMapping> courseSemesterMapping;
	
	@OneToMany(mappedBy="course",cascade=CascadeType.ALL)
	private Set<Assignment> assignments;
	
	@OneToMany(mappedBy="course",cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private Set<UserAssignmentMapping> userAssignmentMapping;
	
	@OneToMany(mappedBy="course",cascade=CascadeType.DETACH)
	private Set<StudentQuery> query;
	
	public Set<StudentQuery> getQuery() {
		return query;
	}
	public void setQuery(Set<StudentQuery> query) {
		this.query = query;
	}
	public Set<CourseSemesterMapping> getCourseSemesterMapping() {
		return courseSemesterMapping;
	}
	public void setCourseSemesterMapping(Set<CourseSemesterMapping> courseSemesterMapping) {
		this.courseSemesterMapping = courseSemesterMapping;
	}
	public Set<UserAssignmentMapping> getUserAssignmentMapping() {
		return userAssignmentMapping;
	}
	public void setUserAssignmentMapping(Set<UserAssignmentMapping> userAssignmentMapping) {
		this.userAssignmentMapping = userAssignmentMapping;
	}
	public Set<Assignment> getAssignments() {
		return assignments;
	}
	public void setAssignments(Set<Assignment> assignments) {
		this.assignments = assignments;
	}
	public Set<StudentCourseSemMap> getStudentCourseSemMapping() {
		return studentCourseSemMapping;
	}
	public void setStudentCourseSemMapping(Set<StudentCourseSemMap> studentCourseSemMapping) {
		this.studentCourseSemMapping = studentCourseSemMapping;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	
}
