package com.leopaulmartin.spring.leboncoinecole.web.controllers;


import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import com.leopaulmartin.spring.leboncoinecole.services.SchoolService;
import com.leopaulmartin.spring.leboncoinecole.web.dto.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SchoolService schoolService;

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		List<Category> allCategories = categoryService.getAllCategories();
		model.addAttribute("allCategories", allCategories);
//		List<Category> top5categories = categoryService.getAllCategoriesSortedByAnnounce();
		model.addAttribute("top5categories", allCategories.subList(0, 5));
		model.addAttribute("top5schools", schoolService.getAllSchools().subList(0, 5));
		model.addAttribute("allSchools", schoolService.getAllSchools());
		model.addAttribute("searchForm", new SearchForm());

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