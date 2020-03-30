package com.leopaulmartin.spring.leboncoinecole.services;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryService {
	List<Category> getAllCategories();

//	List<Category> getAllCategoriesSortedByAnnounce();

	Category getCategoryById(Long id);

	Category getCategoryByLabel(String label);

	Category createCategory(Category category);

	Category updateCategory(Long id, Category category);

	void deleteCategoryById(Long id);

	void deleteAllCategories();

	boolean isCategoryValid(Category category);
}
