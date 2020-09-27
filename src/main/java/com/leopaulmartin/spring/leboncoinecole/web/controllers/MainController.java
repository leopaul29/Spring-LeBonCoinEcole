package com.leopaulmartin.spring.leboncoinecole.web.controllers;


import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;
import com.leopaulmartin.spring.leboncoinecole.services.AnnouncementService;
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
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private AnnouncementService announcementService;
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

	@GetMapping(path = {"/announces/{id}"})
	public String showAnnouncementById(Model model,
									   @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		if (id.isPresent()) {
			Announcement announcement = announcementService.getAnnouncementById(id.get());
			model.addAttribute("announcement", announcement);
			return "display-one";
		} else {
			return "error-404";
		}
	}

	@GetMapping(path = {"/category/{label}"})
	public String showAnnouncementsByCategory(Model model, @PathVariable("label") Optional<String> label)
			throws RecordNotFoundException {
		if (label.isPresent()) {
			Category category = categoryService.getCategoryByLabel(label.get());
			List<Announcement> allAnnouncements = announcementService.getAnnouncementsByCategory(category.getCategoryId());
			if (allAnnouncements==null){
				allAnnouncements = new ArrayList<>();
			}
			model.addAttribute("allAnnouncements", allAnnouncements);
			return "search";
		} else {
			return "error-404";
		}
	}
}