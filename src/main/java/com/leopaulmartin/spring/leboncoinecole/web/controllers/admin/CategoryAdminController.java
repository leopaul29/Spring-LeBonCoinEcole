package com.leopaulmartin.spring.leboncoinecole.web.controllers.admin;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.services.AnnouncementService;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryAdminController {
	private static final Logger logger = LoggerFactory.getLogger(CategoryAdminController.class);
	private static final String VIEW = "admin/categories";
	private static final String REDIRECT = "redirect:/";
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

	@RequestMapping(path = "/delete/{id}")
	public String deleteCategoryById(Model model, @PathVariable("id") Long id)
			throws RecordNotFoundException {
		service.deleteCategoryById(id);
		return REDIRECT + VIEW;
	}
}
