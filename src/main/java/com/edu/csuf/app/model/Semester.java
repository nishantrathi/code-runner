package com.edu.csuf.app.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="semester")
public class Semester {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="semId")
	private int semId;
	
	@Column(name="semName")
	private String semName;
	
	@Column(name="status")
	private int active;
	
	//sem - came from StudentCourseSemMap class
	@OneToMany(mappedBy="sem",cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private Set<StudentCourseSemMap> studentCourseSemMapping;
	
	@OneToMany(mappedBy="sem",cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private Set<CourseSemesterMapping> courseSemesterMapping;
	
	//sem - came from assignment class
	@OneToMany(mappedBy="sem",cascade=CascadeType.ALL)
	private Set<Assignment> assignments;
	
	@OneToMany(mappedBy="sem",cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private Set<UserAssignmentMapping> userAssignmentMapping;
	
	@OneToMany(mappedBy="sem",cascade={CascadeType.DETACH})
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

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
	
	public Set<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(Set<Assignment> assignments) {
		this.assignments = assignments;
	}

	public Set<CourseSemesterMapping> getCourseSemesterMapping() {
		return courseSemesterMapping;
	}

	public void setCourseSemesterMapping(Set<CourseSemesterMapping> courseSemesterMapping) {
		this.courseSemesterMapping = courseSemesterMapping;
	}

	public Set<StudentCourseSemMap> getStudentCourseSemMapping() {
		return studentCourseSemMapping;
	}

	public void setStudentCourseSemMapping(Set<StudentCourseSemMap> studentCourseSemMapping) {
		this.studentCourseSemMapping = studentCourseSemMapping;
	}

	public int getSemId() {
		return semId;
	}

	public void setSemId(int semId) {
		this.semId = semId;
	}

	public String getSemName() {
		return semName;
	}

	public void setSemName(String semName) {
		this.semName = semName;
	}

}
