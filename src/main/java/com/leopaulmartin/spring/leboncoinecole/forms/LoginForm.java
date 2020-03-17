package com.leopaulmartin.spring.leboncoinecole.forms;

import java.io.Serializable;

public class LoginForm implements Serializable {
	public String username;
	public String password;

	public LoginForm() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
