package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import com.leopaulmartin.spring.leboncoinecole.utils.Utils;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;

@Entity(name = "users")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Instant created;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
					name = "user_id", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "role_id"))
	private Collection<Role> roles;

	// Constructor

	public User() {
		this.created = Instant.now();
	}

	public User(String firstName, String lastName, String email, String password) {
		this();

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public User(String firstName, String lastName, String email, String password, Collection<Role> roles) {
		this();

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	// Getters & Setters

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreated() {
		return Utils.getFormattedDate(created);
	}
//	public Instant getCreated() {
//		return created;
//	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	// Override

	@Override
	public String toString() {
		return "User{" +
				"id=" + userId +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", password='" + "***********" + '\'' +
				", created=" + created +
				", roles=" + roles +
				'}';
	}
}
