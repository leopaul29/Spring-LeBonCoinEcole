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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AnnounceController {
	public static final String REDIRECT = "redirect:/";
	private static final Logger logger = LoggerFactory.getLogger(AnnounceController.class);
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

	@GetMapping(path = "/quick-search")
	public String handleQuickSearchRequest(Model model, @RequestParam("q") String q) {
		SearchForm searchForm = new SearchForm();
		searchForm.setKeywordsInput(q);
		return REDIRECT+"search";
	}

	// request GET sample: https://.../recherche/?category=43&text=nitendo&locations=Lyon__45.76071825414269_4.836251707178035_7600&search_in=subject
	@GetMapping("/search")
	public String handleSearchRequest(@ModelAttribute("searchForm") SearchForm searchForm, BindingResult errors, Model model) {
		//TODO: foreach announce: display title, category, school and creation date
		logger.debug("searchForm:" + searchForm.toString());
		//TODO: handle search form errors and display it in a alert box
		logger.debug("errors:" + errors.toString());

		List<Announcement> allAnnouncements = new ArrayList<>();

		allAnnouncements = announcementService.getAllAnnouncements();
		if (searchForm.getType()==null && searchForm.getCategoryId()==-1 &&
				searchForm.getKeywordsInput().isEmpty() && searchForm.getSchoolId()==-1) {
			logger.debug("no filter");
			allAnnouncements = announcementService.getAllAnnouncements();
		} else if (searchForm.getType()!=null && searchForm.getCategoryId()==-1 &&
				searchForm.getKeywordsInput().isEmpty() && searchForm.getSchoolId()==-1) {
			logger.debug("filter by type only");
			allAnnouncements = announcementService.getAnnouncementsByType(searchForm.getType());
		} else if(searchForm.getType()==null && searchForm.getCategoryId()!=-1 &&
				searchForm.getKeywordsInput().isEmpty() && searchForm.getSchoolId()==-1){
			logger.debug("filter by category only");
			allAnnouncements = announcementService.getAnnouncementsByCategory(searchForm.getCategoryId());
		} else if(searchForm.getType()==null && searchForm.getCategoryId()==-1 &&
				!searchForm.getKeywordsInput().isEmpty() && searchForm.getSchoolId()==-1){
			logger.debug("filter by keywordInput only");
			allAnnouncements = announcementService.getAnnouncementsByKeywordInput(searchForm.getKeywordsInput());
		} else {
//			allAnnouncements = announcementService.getAnnouncementsFromSearchForm(searchForm);
			allAnnouncements = announcementService.getAllAnnouncements();
		}
		model.addAttribute("allAnnouncements", allAnnouncements);
		return "search";
	}

	//TODO: use AJAX to propose words for the keyword field (with elastic search ?)
	//TODO: save the research

	@PostMapping("/announces/createUpdateAnnouncement")
	public String handleCreateUpdateAnnouncementRequest(Model model,
														@ModelAttribute("announcement") Announcement announcement,
														BindingResult errors) {
		logger.debug("announcement: "+announcement);
		if(!errors.hasErrors()) {
			Announcement createUpdateAnnounce = announcementService.createOrUpdateAnnouncement(announcement);
			Long id = createUpdateAnnounce.getAnnouncementId();
			return REDIRECT + "announces/view/"+id;
		} else {
			//TODO manage error
			return "add-edit-announcement";
		}
	}

	@GetMapping(path = {"/announces/view/{id}"})
	public String showAnnouncementById(Model model, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		if (id.isPresent()) {
			Announcement announcement = announcementService.getAnnouncementById(id.get());
			model.addAttribute("announcement", announcement);
			return "display-one";
		} else {
			return "error-404";
		}
	}

	@GetMapping(path = {"/announces/category/{label}"})
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
