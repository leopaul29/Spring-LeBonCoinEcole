package com.leopaulmartin.spring.leboncoinecole.restcontrollers;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.RestPreconditions;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Category rest API
 * link about REST API validation: https://howtodoinjava.com/spring-boot2/spring-rest-request-validation/
 */

@RestController
@RequestMapping(path = "/api/v1/categories")

/*
If there’s no use case where the controller has to be injected or manipulated through a direct reference, then I prefer not to declare it as public.
 */
class CategoryRestController {
	private static final Logger logger = LoggerFactory.getLogger(CategoryRestController.class);

	@Autowired
	private CategoryService service;

	/*
		Sample of ResponseEntity creation
		http://zetcode.com/springboot/responseentity/
		Spring JavaDoc
		https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html
	 */
	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = service.getAllCategories();

		return ResponseEntity.ok()
				.header("Responded", "CategoryRestController")
				.header("Method", "getAllCategories")
				.body(categories);
	}

	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
		Category category = service.getCategoryById(id);

		return ResponseEntity.ok()
				.header("Responded", "CategoryRestController")
				.header("Method", "getCategoryById")
				.body(category);
	}

	@GetMapping(path = "/label/{label}", produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Category> getCategoryByLabel(@PathVariable("label") String label) {
		Category category = service.getCategoryByLabel(label);

		return ResponseEntity.ok()
				.header("Responded", "CategoryRestController")
				.header("Method", "getCategoryByLabel")
				.body(category);
	}

	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
		RestPreconditions.checkNotNull(category);

		Category newCategory = service.createCategory(category);

		return ResponseEntity.status(HttpStatus.CREATED)
				.header("Responded", "CategoryRestController")
				.header("Method", "createCategory")
				.body(newCategory);
	}

	@PutMapping(path = "/{id}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) {
		RestPreconditions.checkNotNull(category);

		Category updatedCategory = service.updateCategory(id, category);

		return ResponseEntity.ok()
				.header("Responded", "CategoryRestController")
				.header("Method", "updateCategory")
				.body(updatedCategory);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> deleteCategoryById(@PathVariable("id") Long id) {
		service.deleteCategoryById(id);

		return ResponseEntity.ok()
				.header("Responded", "CategoryRestController")
				.header("Method", "deleteCategoryById")
				.build();
	}

	@DeleteMapping
	public ResponseEntity<Object> deleteAllCategory() throws AccessDeniedException {
//		throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete Categories");
		throw new AccessDeniedException("Cannot delete Categories");
//		service.deleteAllCategories();
//
//		return ResponseEntity.ok()
//				.header("Responded", "CategoryRestController")
//				.header("Method", "deleteAllCategory")
//				.build();
	}
}
