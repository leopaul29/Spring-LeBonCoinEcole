package com.leopaulmartin.spring.leboncoinecole.forms;

import java.io.Serializable;

public class CategoryForm implements Serializable {
	String label;

	public CategoryForm() {
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
