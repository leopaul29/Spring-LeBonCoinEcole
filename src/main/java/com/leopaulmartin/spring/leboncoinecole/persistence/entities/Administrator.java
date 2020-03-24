package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "administrators")
@Table(name = "administrators")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Administrator extends User {
	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "administrator_id")
	private Long administratorId;

	@Column(name = "username", length = 30, nullable = false)
	@NotNull
	private String username;

	@Column(name = "password", length = 30, nullable = false)
	@NotNull
	private String password;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email")
	private String email;*/

	public Administrator() {
		super();

		this.role = "ROLE_ADMIN";
	}

	public Administrator(String username, String password) {
		this();

		this.username = username;
		this.password = password;
	}

	/*
	//	public String getUsername() {
	//		return username;
	//	}
	//
	//	public void setUsername(String username) {
	//		this.username = username;
	//	}
	//
	//	public String getPassword() {
	//		return password;
	//	}
	//
	//	public void setPassword(String password) {
	//		this.password = password;
	//	}
	//
	//	public Long getAdministratorId() {
	//		return administratorId;
	//	}
	//
	//	public void setAdministratorId(Long administratorId) {
	//		this.administratorId = administratorId;
	//	}

	//	public String getPhoneNumber() {
	//		return phoneNumber;
	//	}
	//
	//	public void setPhoneNumber(String phoneNumber) {
	//		this.phoneNumber = phoneNumber;
	//	}
	//
	//	public String getEmail() {
	//		return email;
	//	}
	//
	//	public void setEmail(String email) {
	//		this.email = email;
	//	}
	*/
	@Override
	public String toString() {
		return "Administrator{" +
//				"administratorId=" + administratorId +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
