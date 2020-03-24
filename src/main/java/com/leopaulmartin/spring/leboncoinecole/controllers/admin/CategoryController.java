package com.leopaulmartin.spring.leboncoinecole.controllers.admin;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.services.AnnouncementService;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	public static final String VIEW = "admin/categories";
	public static final String REDIRECT = "redirect:/";
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	@Autowired
	private CategoryService service;
	@Autowired
	private AnnouncementService announcementService;

	@ModelAttribute("allCategories")
	public List<Category> populateCategories() {
		return service.getAllCategories();
	}

//	@ModelAttribute(value = "countAnnouncementByCategory")
//	public int populateCountAnnouncementByCategory(Long categoryId) {
//		return announcementService.countAnnouncementForCategory(categoryId);
//	}

	@GetMapping
	public String showCategories() {
		return VIEW;
	}

	@PostMapping
	public String createCategory(@ModelAttribute("categoryLabel") String categoryLabel) {
		Category newCategory = new Category(categoryLabel);
		boolean categoryValid = service.isCategoryValid(newCategory);
		if (categoryValid) service.createCategory(newCategory);
		// TODO: else return and display error
		return REDIRECT + VIEW;
	}

	@DeleteMapping("/{id}")
	public String deleteCategory(@RequestParam("removeRow") Long categoryId) {
		logger.error("deleteCategory:" + categoryId);
		service.deleteCategoryById(categoryId);

		return REDIRECT + VIEW;
	}
}
