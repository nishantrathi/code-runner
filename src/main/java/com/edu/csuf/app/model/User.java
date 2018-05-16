package com.edu.csuf.app.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="uId")
	private long userId;

	@Column(name="firstName")
	private String firstName;

	@Column(name="lastName")
	private String lastName;

	@Column(name="emailId")
	private String emailId;

	@Column(name="cwid")
	private long cwid;

	@Column(name="type")
	private int type;

	@Column(name="createdOn")
	private Date createdOn;

	@Column(name="status")
	private String status;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@JsonBackReference
	@OneToMany(mappedBy="user",cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private Set<StudentCourseSemMap> studentCourseSemMapping;

	@JsonBackReference
	@OneToMany(mappedBy="user",cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private Set<UserAssignmentMapping> userAssignmentMapping;
	

	@OneToMany(mappedBy="user",cascade=CascadeType.DETACH)
	private Set<Assignment> assignments;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.DETACH)
	private Set<StudentQuery> query;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.DETACH)
	private Set<QueryAnswer> queryAnswer;
	
	public Set<QueryAnswer> getQueryAnswer() {
		return queryAnswer;
	}
	public void setQueryAnswer(Set<QueryAnswer> queryAnswer) {
		this.queryAnswer = queryAnswer;
	}

	public Set<StudentQuery> getQuery() {
		return query;
	}
	public void setQuery(Set<StudentQuery> query) {
		this.query = query;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public long getCwid() {
		return cwid;
	}
	public void setCwid(long cwid) {
		this.cwid = cwid;
	}
	
	public Set<UserAssignmentMapping> getUserAssignmentMapping() {
		return userAssignmentMapping;
	}
	public void setUserAssignmentMapping(Set<UserAssignmentMapping> userAssignmentMapping) {
		this.userAssignmentMapping = userAssignmentMapping;
	}
}
