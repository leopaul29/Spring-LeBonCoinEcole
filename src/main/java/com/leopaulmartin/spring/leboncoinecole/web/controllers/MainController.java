package com.leopaulmartin.spring.leboncoinecole.web.controllers;


import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import com.leopaulmartin.spring.leboncoinecole.services.SchoolService;
import com.leopaulmartin.spring.leboncoinecole.web.dto.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SchoolService schoolService;

	@ModelAttribute("allCategories")
	public List<Category> populateCategories() {
		return categoryService.getAllCategories();
	}
	@ModelAttribute("allSchools")
	public List<School> populateSchools() {
		return schoolService.getAllSchools();
	}
	@ModelAttribute("searchForm")
	public SearchForm populateSearchForm() { return new SearchForm(); }

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("top5categories", categoryService.getAllCategories().subList(0, 5));
		model.addAttribute("top5schools", schoolService.getAllSchools().subList(0, 5));
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/access-denied")
	public String accessDenied() {
		return "/error/access-denied";
	}
}