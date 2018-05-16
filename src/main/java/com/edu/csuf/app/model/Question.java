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

@Entity
@Table(name="question")
public class Question {
	@Id
	@Column(name="questionId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int questionId;
	
	@Column(name="questionNumber")
	private int questionNumber;
	
	@Column(name="questionInfo")
	private String questionDetail;
	
	@ManyToOne
	@JoinColumn(name="assignId")
	private Assignment assign;
	
	@OneToMany(mappedBy="question",cascade=CascadeType.DETACH)
	private Set<StudentQuery> query;
	
	public Set<StudentQuery> getQuery() {
		return query;
	}
	public void setQuery(Set<StudentQuery> query) {
		this.query = query;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}
	public String getQuestionDetail() {
		return questionDetail;
	}
	public void setQuestionDetail(String questionDetail) {
		this.questionDetail = questionDetail;
	}
	public Assignment getAssign() {
		return assign;
	}
	public void setAssign(Assignment assign) {
		this.assign = assign;
	}

}
