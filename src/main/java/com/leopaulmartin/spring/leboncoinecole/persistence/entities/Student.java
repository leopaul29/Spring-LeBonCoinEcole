package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Entity(name = "students")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private Long studentId;

	@Column(name = "username", length = 30, nullable = false, unique = true)
	@NotNull(message = "Cannot be null")
	private String username;

	@Column(name = "password", length = 30, nullable = false)
	@NotNull(message = "Cannot be null")
	private String password;

	@Column(name = "first_name", length = 30)
	private String firstName;

	@Column(name = "last_name", length = 30)
	private String lastName;

	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name = "photo")
	private byte[] photo;

	@OneToMany(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
//	@JoinTable(
//			name = "student_phonenumbers",
//			joinColumns = @JoinColumn(name = "student_id"),
//			inverseJoinColumns = @JoinColumn(name = "phonenumber_id"))
	private List<PhoneNumber> phonenumbers;

	@OneToMany(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
//	@JoinTable(
//			name = "student_emails",
//			joinColumns = @JoinColumn(name = "student_id"),
//			inverseJoinColumns = @JoinColumn(name = "email_id"))
	private List<Email> emails;

	@ManyToOne
//	@JoinTable(
//			name = "school_students",
//			joinColumns = @JoinColumn(name = "student_id"),
//			inverseJoinColumns = @JoinColumn(name = "school_id"))
	private School school;

	@OneToMany(targetEntity = com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement.class,
			mappedBy = "student",
			cascade = {CascadeType.REMOVE}, orphanRemoval = true)
//	@JoinTable(
//			name = "student_announcements",
//			joinColumns = @JoinColumn(name = "student_id"),
//			inverseJoinColumns = @JoinColumn(name = "announcement_id"))
	private List<Announcement> announcements;

	public Student() {
	}

	public Student(@NotNull(message = "Cannot be null") String username, @NotNull(message = "Cannot be null") String password) {
		this.username = username;
		this.password = password;
	}

	public Student(@NotNull(message = "Cannot be null") String username, @NotNull(message = "Cannot be null") String password,
				   String firstName, String lastName,
				   List<PhoneNumber> phonenumbers, List<Email> emails) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phonenumbers = phonenumbers;
		this.emails = emails;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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

	public List<PhoneNumber> getPhonenumbers() {
		return phonenumbers;
	}

	public void setPhonenumbers(List<PhoneNumber> phonenumbers) {
		this.phonenumbers = phonenumbers;
	}

	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
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

	@Override
	public String toString() {
		return "Student{" +
				"studentId=" + studentId +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", photo=" + Arrays.toString(photo) +
				", phoneNumbers=" + phonenumbers +
				", emails=" + emails +
				", school=" + school +
				", announcements=" + announcements +
				'}';
	}
}
