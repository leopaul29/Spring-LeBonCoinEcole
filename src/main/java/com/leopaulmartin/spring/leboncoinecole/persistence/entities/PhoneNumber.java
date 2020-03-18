package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "phonenumbers")
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class PhoneNumber {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "phonenumber_id")
	private Long phonenumberId;

	@Column(name = "number", length = 10, nullable = false, unique = true)
	@NotNull
	private String number;

	public PhoneNumber() {
	}

	public PhoneNumber(String number) {
		this.number = number;
	}

	public Long getPhonenumberId() {
		return phonenumberId;
	}

	public void setPhonenumberId(Long phonenumberId) {
		this.phonenumberId = phonenumberId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "PhoneNumber{" +
				"phonenumberId=" + phonenumberId +
				", number='" + number + '\'' +
				'}';
	}
}
