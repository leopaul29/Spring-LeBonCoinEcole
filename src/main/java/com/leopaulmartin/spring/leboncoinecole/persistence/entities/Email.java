package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "emails")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Email {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "email_id")
	private Long emailId;

	@Column(name = "email", length = 50, nullable = false, unique = true)
	@Size(min = 5, max = 50)
	@NotNull
	private String email;

	public Email() {
	}

	public Email(String email) {
		this.email = email;
	}

	public Long getEmailId() {
		return emailId;
	}

	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Email{" +
				"emailId=" + emailId +
				", email='" + email + '\'' +
				'}';
	}
}
