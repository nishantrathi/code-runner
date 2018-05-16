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
@Table(name="studentQueryAnswer")
public class QueryAnswer {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="queryAnsId")
	private long queryAnsId;
	
	@Column(name="queryAnswer")
	private String queryAnswer;
	
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE})
	@JoinColumn(name="userId")
	private User user;
	
	@Valid
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE})
	@JoinColumn(name="qryId")
	private StudentQuery studentQuery;
	
	@Column(name="submittedDate")
	private String submittedDate;
	
	public long getQueryAnsId() {
		return queryAnsId;
	}

	public void setQueryAnsId(long queryAnsId) {
		this.queryAnsId = queryAnsId;
	}

	public String getQueryAnswer() {
		return queryAnswer;
	}

	public void setQueryAnswer(String queryAnswer) {
		this.queryAnswer = queryAnswer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public StudentQuery getStudentQuery() {
		return studentQuery;
	}

	public void setStudentQuery(StudentQuery studentQuery) {
		this.studentQuery = studentQuery;
	}

	public String getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}

	
}
