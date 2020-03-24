package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class User {
	@Column(name = "username", length = 30, nullable = false, unique = true)
	protected String username;
	@Column(name = "password", length = 30, nullable = false)
	@JsonIgnore
	protected String password;
	@Column(name = "email")
	protected String email;
	@Column(name = "phone_number")
	protected String phoneNumber;
	@Column(name = "role")
	protected String role;
	@Column(name = "created")
	protected Instant created;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "user_id")
	private Long userId;

	protected User() {
		this.role = "ROLE_USER";
		this.created = Instant.now();
	}

	// Getter & Setter

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Instant getCreated() {
		return created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	// Override

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", role='" + role + '\'' +
				", created=" + created +
				'}';
	}
}
