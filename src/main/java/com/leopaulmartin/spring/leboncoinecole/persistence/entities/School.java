package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "schools")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class School {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "school_id")
	private Long schoolId;

	@Column(name = "name", length = 150, nullable = false, unique = true)
	@Size(min = 3, max = 150)
	@NotNull
	private String name;

	@Column(name = "link", length = 200)
	private String link;

	@OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private Address address;

	@OneToMany(targetEntity = com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student.class,
			mappedBy = "school",
			fetch = FetchType.LAZY,
			//https://howtodoinjava.com/hibernate/hibernate-jpa-cascade-types/
			cascade = {CascadeType.REMOVE})
	private List<Student> students;

	public School() {
	}

	public School(String name, Address address) {
		this.name = name;
		this.address = address;
	}

	public School(String name, Address address, String link) {
		this(name, address);
		this.link = "www.onisep.fr/http/redirection/etablissement/identifiant/" + link;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "School{" +
				"schoolId=" + schoolId +
				", name='" + name + '\'' +
				", link='" + link + '\'' +
				", address=" + address +
				'}';
	}
}
