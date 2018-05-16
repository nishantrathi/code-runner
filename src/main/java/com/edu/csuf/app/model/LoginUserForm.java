package com.edu.csuf.app.model;

import javax.validation.Valid;

public class LoginUserForm {
	@Valid
	private User user;

	@Valid
	private Login login;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	
}
