package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordAlreadyExistException;
import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordIdMismatchException;
import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.CategoryRepository;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//Use @Transactional to cancel the transaction in case of any exception
//https://netsurfingzone.com/spring/spring-transaction-management-example-using-spring-boot/
//https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#transactions
@Transactional
public class CategoryServiceImpl implements CategoryService {

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
		return repository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("", id));
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public Category getCategoryByLabel(String label) throws RecordNotFoundException {
		if (repository.findByLabel(label) == null)
			throw new RecordNotFoundException("label", label);
		return repository.findByLabel(label);
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
			throw new RecordAlreadyExistException("label", category.getLabel());

		return repository.saveAndFlush(category);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Category updateCategory(Long id, Category category) throws RecordIdMismatchException, RecordAlreadyExistException, RecordNotFoundException {
		if (!category.getId().equals(id)) {
			throw new RecordIdMismatchException();
		}
		if (repository.findByLabel(category.getLabel()) != null)
			throw new RecordAlreadyExistException("label", category.getLabel());

		Category existingCategory = repository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("id", id));
		BeanUtils.copyProperties(category, existingCategory, "category_id");
		return repository.saveAndFlush(existingCategory);

//		Optional<Category> existingCategory = repository.findById(id);
//		if (existingCategory.isPresent()) {
//			Category newCategory = existingCategory.get();
//			newCategory.setLabel(category.getLabel());
//			BeanUtils.copyProperties(category, existingCategory, "category_id");
//			return repository.saveAndFlush(newCategory);
//		} else {
//			throw new RecordNotFoundException("id", id);
//		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteCategoryById(Long id) throws RecordNotFoundException {
		repository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("id", id));
		repository.deleteById(id);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteAllCategories() {
		repository.deleteAllCategories();
	}
}
