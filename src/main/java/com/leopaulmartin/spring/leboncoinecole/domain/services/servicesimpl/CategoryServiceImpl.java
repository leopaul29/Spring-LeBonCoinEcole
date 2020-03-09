package com.leopaulmartin.spring.leboncoinecole.domain.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.domain.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.domain.repositories.CategoryRepository;
import com.leopaulmartin.spring.leboncoinecole.domain.services.CategoryService;
import com.leopaulmartin.spring.leboncoinecole.exceptions.RecordAlreadyExistException;
import com.leopaulmartin.spring.leboncoinecole.exceptions.RecordNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
//Use @Transactional to cancel the transaction in case of any exception
//https://netsurfingzone.com/spring/spring-transaction-management-example-using-spring-boot/
//https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#transactions
@Transactional
public class CategoryServiceImpl implements CategoryService {
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryRepository repository;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, timeout = 10)
	/*
	About @Transactional options
	https://netsurfingzone.com/spring/spring-transaction-management-basic/
	 */
	@Override
	public List<Category> getAllCategories() {
		return repository.findAll();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public Category getCategoryById(Long id) throws RecordNotFoundException {
		if (!repository.existsById(id)) throw new RecordNotFoundException("Invalid category id : " + id);
		return repository.getOne(id);
	}

	@Override
	public Category getCategoryByLabel(String label) throws RecordNotFoundException {
		if (repository.findByLabel(label) == null)
			throw new RecordNotFoundException("Invalid category label : " + label);
		return repository.findByLabel(label);
	}

	@Override
	public void deleteCategoryById(Long id) throws RecordNotFoundException {
		if (!repository.existsById(id)) throw new RecordNotFoundException("Invalid category id : " + id);
		repository.deleteById(id);
	}

	/*
	Difference between repository.getOne and findById
	 getOne: Lazy, no properties access required, EntityNotFoundException, better performance
	 findById: Eager, real object mapping to a row in the database, Null, database cost
	https://www.javacodemonk.com/difference-between-getone-and-findbyid-in-spring-data-jpa-3a96c3ff
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Category createCategory(Category category) throws RecordAlreadyExistException {
		if (repository.findByLabel(category.getLabel()) != null)
			throw new RecordAlreadyExistException("Category's label already exist");

		return repository.saveAndFlush(category);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Category updateCategory(Category category) {
		// PATCH will focus on one attribut, PUT will expect all attributes to be passed in.
		//TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload

		Optional<Category> existingCategory = repository.findById(category.getId());
		if (existingCategory.isPresent()) {
			Category newCategory = existingCategory.get();
			BeanUtils.copyProperties(newCategory, existingCategory, "category_id");
			return repository.saveAndFlush(newCategory);
		} else {
			return repository.saveAndFlush(category);
		}
	}
}
