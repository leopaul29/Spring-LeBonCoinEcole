package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity(name = "students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private Long studentId;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name = "photo")
	private byte[] photo;

	@ManyToOne
	private School school;

	//set option to remove the user profile when student profile is deleted
	@OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private User userProfile;

	//TODO: set option to remove all announcement when student profil is deleted
	@OneToMany/*(
			targetEntity = com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement.class,
			mappedBy = "student",
			fetch = FetchType.LAZY,
			cascade = {CascadeType.REMOVE}, orphanRemoval = true)*/
	@JoinTable(
			name = "students_announcements",
			joinColumns = @JoinColumn(
					name = "student_id", referencedColumnName = "student_id"),
			inverseJoinColumns = @JoinColumn(
					name = "announcement_id", referencedColumnName = "announcement_id"))
	private List<Announcement> announcements;

	// Constructor

	public Student() {
	}

	public Student(User user) {
		this.userProfile = user;
	}

//	public Student(String firstName, String lastName) {
//		this();
//
//		this.firstName = firstName;
//		this.lastName = lastName;
//	}
//
//	public Student(String firstName, String lastName, String phoneNumber) {
//		this();
//
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.phoneNumber = phoneNumber;
//	}

	// Getter & Setter

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public User getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(User userProfile) {
		this.userProfile = userProfile;
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
				"studentId=" + studentId +
				", phoneNumber='" + phoneNumber + '\'' +
				", photo=" + (photo != null) +
				", school=" + school +
				", userProfil=" + userProfile +
				", announcements=" + announcements +
				'}';
	}
}
