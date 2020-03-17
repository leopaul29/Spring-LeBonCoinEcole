package com.leopaulmartin.spring.leboncoinecole.controllers.admin;

import com.leopaulmartin.spring.leboncoinecole.forms.CategoryForm;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	public static final String API_ROOT = "admin/categories";
	public static final String REDIRECT = "redirect:/";
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	@Autowired
	private CategoryService service;

	@GetMapping
	public String showAllCategories(Model model) {
		model.addAttribute("allCategories", service.getAllCategories());

		CategoryForm categoryForm = new CategoryForm();
		model.addAttribute("categoryForm", categoryForm);

		return API_ROOT;
	}

	@PostMapping
	public String saveCategory(Model model, @ModelAttribute CategoryForm categoryForm) {
		Category newCategory = new Category(categoryForm.getLabel());

		service.createCategory(newCategory);

		return /*REDIRECT +*/ API_ROOT;
	}

	@RequestMapping("/{id}")
	public String show(@PathVariable("id") Category category, Model model) {

//		model.addAttribute("categories", service.getAllCategories());
		return "admin/category";
	}
}
