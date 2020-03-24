package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/*
Constraint validation annotations
https://docs.oracle.com/javaee/7/api/javax/validation/constraints/package-summary.html
 */
@Entity(name = "categories")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "category_label", length = 50, nullable = false, unique = true)
	// Validation valid the parameter itself
	@Size(min = 3, max = 50, message = "Category's Label must be longer than 3 characters and shorter than 50 characters")
	private String label;

	@OneToMany(targetEntity = com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement.class,
			mappedBy = "category",
			cascade = {CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Announcement> announcements;

	protected Category() {
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

	public List<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
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
