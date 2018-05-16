package com.edu.csuf.app.model;

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
import javax.validation.Valid;

@Entity
@Table(name="studentQuery")
public class StudentQuery {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="queryId")
	private long queryId;
	
	@Column(name="query")
	private String query;
	
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


	@OneToMany(mappedBy="studentQuery",cascade={CascadeType.DETACH})
	private Set<QueryAnswer> queryAnswer;
	

	@Valid
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE})
	@JoinColumn(name="quesId")
	private Question question;
	
	@Column(name="year")
	private int year;
	
	@Column(name="submittedDate")
	private String submittedDate;

	public Set<QueryAnswer> getQueryAnswer() {
		return queryAnswer;
	}

	public void setQueryAnswer(Set<QueryAnswer> queryAnswer) {
		this.queryAnswer = queryAnswer;
	}

	public long getQueryId() {
		return queryId;
	}

	public void setQueryId(long queryId) {
		this.queryId = queryId;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
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

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}

	
	
	
}
