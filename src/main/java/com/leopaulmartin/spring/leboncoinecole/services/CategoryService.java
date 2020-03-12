package com.leopaulmartin.spring.leboncoinecole.services;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordAlreadyExistException;
import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordIdMismatchException;
import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryService {
	List<Category> getAllCategories();

	Category getCategoryById(Long id) throws RecordNotFoundException;

	Category getCategoryByLabel(String label) throws RecordNotFoundException;

	Category createCategory(Category category) throws RecordAlreadyExistException;

	Category updateCategory(Long id, Category category) throws RecordIdMismatchException, RecordAlreadyExistException, RecordNotFoundException;

	void deleteCategoryById(Long id) throws RecordNotFoundException;

	void deleteAllCategories();
}
