package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import javax.persistence.*;

@Entity(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private Long roleId;
	private String name;

	// Constructor

	public Role() {
	}

	// Getter & Setter

	public Role(String name) {
		this.name = name;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Override

	@Override
	public String toString() {
		return "Role{" +
				"roleId=" + roleId +
				", name='" + name + '\'' +
				'}';
	}
}