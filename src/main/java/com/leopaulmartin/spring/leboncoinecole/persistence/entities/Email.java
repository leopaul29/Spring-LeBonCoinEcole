package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity(name = "emails")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Email {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "email_id")
	private Long emailId;

	@Column(name = "email", length = 50)
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
