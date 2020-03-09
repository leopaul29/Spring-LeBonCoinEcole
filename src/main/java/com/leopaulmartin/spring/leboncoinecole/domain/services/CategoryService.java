package com.leopaulmartin.spring.leboncoinecole.domain.services;

import com.leopaulmartin.spring.leboncoinecole.domain.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.exceptions.RecordAlreadyExistException;
import com.leopaulmartin.spring.leboncoinecole.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryService {
	List<Category> getAllCategories();

	Category getCategoryById(Long id);

	Category getCategoryByLabel(String label) throws RecordNotFoundException;

	Category createCategory(Category category) throws RecordAlreadyExistException;

	Category updateCategory(Category category);

	void deleteCategoryById(Long id) throws RecordNotFoundException;
}
