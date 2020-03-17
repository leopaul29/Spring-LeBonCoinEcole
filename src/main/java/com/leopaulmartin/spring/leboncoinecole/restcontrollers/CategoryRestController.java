package com.leopaulmartin.spring.leboncoinecole.restcontrollers;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.RestPreconditions;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.services.AnnouncementService;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
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

	private static final String APPLICATION_JSON_AND_HATEOAS = "application/hal+json";

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AnnouncementService announcementService;

	@GetMapping(produces = APPLICATION_JSON_AND_HATEOAS)
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryService.getAllCategories();
		for (final Category category : categories) {
			Link link = linkTo(CategoryRestController.class).slash(category.getCategoryId()).withSelfRel();
			category.add(link);
		}
	/*
		Sample of ResponseEntity creation
		http://zetcode.com/springboot/responseentity/
		Spring JavaDoc
		https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html
	 */
		return ResponseEntity.ok()
				.header("Responded", "CategoryRestController")
				.header("Method", "getAllCategories")
				.body(categories);
	}

	@GetMapping(path = "/{id}/announcements", produces = APPLICATION_JSON_AND_HATEOAS)
	public Resources<Announcement> getAnnouncementForCategory(@PathVariable("id") final Long categoryId) {
		List<Announcement> announcements = announcementService.getAnnouncementForCategory(categoryId);
		for (final Announcement announcement : announcements) {
			Link selfLink = linkTo(methodOn(CategoryRestController.class).getCategoryById(categoryId)).withSelfRel();
			announcement.add(selfLink);
		}

		Link link = linkTo(methodOn(CategoryRestController.class).getAnnouncementForCategory(categoryId)).withSelfRel();
		Resources<Announcement> result = new Resources<Announcement>(announcements, link);
		return result;
	}

	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
		Category category = categoryService.getCategoryById(id);

		return ResponseEntity.ok()
				.header("Responded", "CategoryRestController")
				.header("Method", "getCategoryById")
				.body(category);
	}

	@GetMapping(path = "/label/{label}", produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Category> getCategoryByLabel(@PathVariable("label") String label) {
		Category category = categoryService.getCategoryByLabel(label);

		return ResponseEntity.ok()
				.header("Responded", "CategoryRestController")
				.header("Method", "getCategoryByLabel")
				.body(category);
	}

	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
		RestPreconditions.checkNotNull(category);

		Category newCategory = categoryService.createCategory(category);

		return ResponseEntity.status(HttpStatus.CREATED)
				.header("Responded", "CategoryRestController")
				.header("Method", "createCategory")
				.body(newCategory);
	}

	@PutMapping(path = "/{id}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) {
		RestPreconditions.checkNotNull(category);

		Category updatedCategory = categoryService.updateCategory(id, category);

		return ResponseEntity.ok()
				.header("Responded", "CategoryRestController")
				.header("Method", "updateCategory")
				.body(updatedCategory);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> deleteCategoryById(@PathVariable("id") Long id) {
		categoryService.deleteCategoryById(id);

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
