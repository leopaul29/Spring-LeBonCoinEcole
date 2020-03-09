package com.leopaulmartin.spring.leboncoinecole.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/*
Constraint validation annotations
https://docs.oracle.com/javaee/7/api/javax/validation/constraints/package-summary.html
 */
@Entity(name = "categories")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_id")
	private Long id;

	@Column(name = "category_label", length = 50, nullable = false, unique = true)
	// Validation valid the parameter itself
	@Size(min = 3, max = 50, message = "Category's label must be longer than 3 characters and shorter than 50 characters")
	private String label;

	public Category() {
		super();
	}

	public Category(final String label) {
		super();

		this.label = label;
	}

	// Getter and Setter

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	//

	@Override
	public String toString() {
		return "Category{" +
				"id=" + id +
				", label='" + label + '\'' +
				'}';
	}
}
