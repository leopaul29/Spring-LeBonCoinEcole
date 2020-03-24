package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity(name = "students")
@Table(name = "students")
public class Student extends User {
	@Column(name = "first_name", length = 30)
	private String firstName;

	@Column(name = "last_name", length = 30)
	private String lastName;

	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name = "photo")
	private byte[] photo;

	@ManyToOne
	private School school;

	@OneToMany(targetEntity = com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement.class,
			mappedBy = "student",
			fetch = FetchType.LAZY,
			cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Announcement> announcements;

	public Student() {
		super();

		this.role = "ROLE_STUDENT";
	}

	public Student(String username, String password) {
		this();

		this.username = username;
		this.password = password;
	}

	public Student(String username, String password, String firstName, String lastName, String email, String phoneNumber) {
		this();

		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	// Getter & Setter

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public List<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
	}

	// Override

	@Override
	public String toString() {
		return "Student{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", photo=" + Arrays.toString(photo) +
				", school=" + school +
				", announcements=" + announcements +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", role='" + role + '\'' +
				", created=" + created +
				'}';
	}
}
