package com.leopaulmartin.spring.leboncoinecole.controllers.admin;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@RequestMapping("/{id}")
	String showCategory(@PathVariable("id") Category category, Model model) {

//		model.addAttribute("categories", service.getAllCategories());
		return "admin/category";
	}
}
