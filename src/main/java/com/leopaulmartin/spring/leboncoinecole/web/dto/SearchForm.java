package com.leopaulmartin.spring.leboncoinecole.web.dto;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;

import java.io.Serializable;

public class SearchForm implements Serializable {

	public String type;
	public Long categoryId;
	public String keywordsInput;
	public Long schoolId;

	public SearchForm() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getKeywordsInput() {
		return keywordsInput;
	}

	public void setKeywordsInput(String keywordsInput) {
		this.keywordsInput = keywordsInput;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	@Override
	public String toString() {
		return "SearchForm{" +
				"type='" + type + '\'' +
				", categoryId=" + categoryId +
				", keywordsInput='" + keywordsInput + '\'' +
				", schoolId=" + schoolId +
				'}';
	}
}
