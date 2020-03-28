package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity(name = "students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private Long studentId;

	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name = "photo")
	private byte[] photo;

	@ManyToOne
	private School school;

	@OneToOne
	private User userProfil;

	@OneToMany//(
//			targetEntity = com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement.class,
//			mappedBy = "student",
//			fetch = FetchType.LAZY,
//			cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	@JoinTable(
			name = "students_announcements",
			joinColumns = @JoinColumn(
					name = "student_id", referencedColumnName = "student_id"),
			inverseJoinColumns = @JoinColumn(
					name = "announcement_id", referencedColumnName = "announcement_id"))
	private List<Announcement> announcements;

	public Student() {
	}

	public Student(String firstName, String lastName) {
		this();

		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(String username, String password, String firstName, String lastName, String email, String phoneNumber) {
		this();

//		this.username = username;
//		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
//		this.email = email;
//		this.phoneNumber = phoneNumber;
	}

	// Getter & Setter

	public User getUserProfil() {
		return userProfil;
	}

	public void setUserProfil(User userProfil) {
		this.userProfil = userProfil;
	}

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
				", phoneNumber='" + phoneNumber +
				'}';
	}
}
