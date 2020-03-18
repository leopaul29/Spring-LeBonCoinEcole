package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.Size;

/*
Constraint validation annotations
https://docs.oracle.com/javaee/7/api/javax/validation/constraints/package-summary.html
 */
@Entity(name = "categories")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category extends ResourceSupport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "category_label", length = 50, nullable = false, unique = true)
	// Validation valid the parameter itself
	@Size(min = 3, max = 50, message = "Category's label must be longer than 3 characters and shorter than 50 characters")
	private String label;

	public Category() {
	}

	public Category(final String label) {
		this.label = label;
	}

	// Getter and Setter

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
				"categoryId=" + categoryId +
				", label='" + label + '\'' +
				'}';
	}
}
