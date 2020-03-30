package com.leopaulmartin.spring.leboncoinecole.web.dto;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;

import java.io.Serializable;

public class SearchForm implements Serializable {

	public String type;
	public Category category;
	public String keywordsInput;
	public School school;

	public SearchForm() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getKeywordsInput() {
		return keywordsInput;
	}

	public void setKeywordsInput(String keywordsInput) {
		this.keywordsInput = keywordsInput;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
}
