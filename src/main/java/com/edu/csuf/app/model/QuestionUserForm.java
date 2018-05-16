package com.edu.csuf.app.model;

import javax.validation.Valid;

public class QuestionUserForm {
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	

	@Valid
	private User user;

	@Valid
	private Question question;
}
