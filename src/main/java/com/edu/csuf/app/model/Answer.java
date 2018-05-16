package com.edu.csuf.app.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="answer",uniqueConstraints=
@UniqueConstraint(columnNames={"quesId"}))
public class Answer {

	public long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(long answerId) {
		this.answerId = answerId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(Date submittedOn) {
		this.submittedOn = submittedOn;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="answerId")
	private long answerId;
	
	@Column(name="path")
	private String path;
	
	@Column(name="comments")
	private String comment;
	
	@Column(name="submittedOn")
	private Date submittedOn;
	
	@OneToOne
	@JoinColumn(name="quesId")
	private Question question;
	
	@OneToOne
	@JoinColumn(name="assignId")
	private Assignment assignment;
	
	@OneToOne
	@JoinColumn(name="userId")
	private User user;
	
	@Transient
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
