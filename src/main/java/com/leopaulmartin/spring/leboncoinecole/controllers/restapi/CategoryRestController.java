package com.leopaulmartin.spring.leboncoinecole.controllers.restapi;

import com.leopaulmartin.spring.leboncoinecole.domain.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.domain.services.CategoryService;
import com.leopaulmartin.spring.leboncoinecole.exceptions.RecordAlreadyExistException;
import com.leopaulmartin.spring.leboncoinecole.exceptions.RecordNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Category rest API
 * link about REST API validation: https://howtodoinjava.com/spring-boot2/spring-rest-request-validation/
 */

@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryRestController {
	private static final Logger logger = LoggerFactory.getLogger(CategoryRestController.class);

	@Autowired
	private CategoryService service;

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = service.getAllCategories();

		return ResponseEntity.ok()
				.header("Responded", "CategoryRestController")
				.header("Method", "getAllCategories")
				.body(categories);
	}

	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id)
			throws RecordNotFoundException {
		Category category = service.getCategoryById(id);

		return ResponseEntity.ok()
				.header("Responded", "CategoryRestController")
				.header("Method", "getCategoryById")
				.body(category);
	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) throws RecordAlreadyExistException {
		service.createCategory(category);

		return ResponseEntity.ok()
				.header("Responded", "CategoryRestController")
				.header("Method", "createCategory")
				.body(category);
	}

//	/*
//	Sample of ResponseEntity creation
//	http://zetcode.com/springboot/responseentity/
//	Spring JavaDoc
//	https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html
//	 */
//	@PutMapping(path = "/", consumes = "application/json", produces = "application/json")
//	@RequestMapping(value = "{id}")
//	public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category)
//			throws RecordNotFoundException {
//		Category updatedCategory = service.createOrUpdateCategory(category);
//		return ResponseEntity.ok()
//				.header("Responded", "CategoryRestController")
//				.body(updatedCategory);
////		return new ResponseEntity<Category>(updatedCategory, new HttpHeaders(), HttpStatus.OK);
//	}
//
//	@DeleteMapping(path = "/")
//	@RequestMapping(value = "{id}")
//	public ResponseEntity<Object> deleteEmployeeById(@PathVariable("id") Long id)
//			throws RecordNotFoundException {
//		service.deleteCategoryById(id);
//
//		return ResponseEntity.noContent().header("Responded", "CategoryRestController").build();
////		return new ResponseEntity(new HttpHeaders(), HttpStatus.FORBIDDEN); //TODO to test
//	}
}
