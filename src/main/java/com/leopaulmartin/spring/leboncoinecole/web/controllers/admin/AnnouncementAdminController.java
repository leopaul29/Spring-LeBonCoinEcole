package com.leopaulmartin.spring.leboncoinecole.web.controllers.admin;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
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
import java.util.Optional;

@Controller
@RequestMapping("/admin/announcements")
public class AnnouncementAdminController {
	public static final String VIEW = "admin/announcements";
	public static final String REDIRECT = "redirect:/";
	private static final Logger logger = LoggerFactory.getLogger(AnnouncementAdminController.class);
	@Autowired
	private AnnouncementService service;
	@Autowired
	private CategoryService categoryService;

	@ModelAttribute("allCategories")
	public List<Category> populateCategories() {
		return categoryService.getAllCategories();
	}

	@GetMapping
	public String showAnnouncements(Model model) {
		model.addAttribute("allAnnouncements", service.getAllAnnouncements());
		return VIEW;
	}

//	@PostMapping
//	public String createAnnouncement(Model model, @ModelAttribute("announcementForm") AnnouncementForm announcementForm) {
//		// create announcement
//		String title = announcementForm.getTitle();
//		String description = announcementForm.getDescription();
//		float price = announcementForm.getPrice();
//		// get category
//		Long categoryId = announcementForm.getCategoryId();
//		Category category = categoryService.getCategoryById(categoryId);
//		//create announcement
//		Announcement newAnnouncement = new Announcement(title, description, category, price);
//		// save announcement
//		service.createAnnouncement(newAnnouncement);
//		// TODO: else return and display error
//		return REDIRECT + VIEW;
//	}


	@GetMapping(path = {"/edit", "/edit/{id}"})
	public String editAnnouncementById(Model model, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		if (id.isPresent()) {
			Announcement announcement = service.getAnnouncementById(id.get());
			model.addAttribute("announcement", announcement);
		} else {
			model.addAttribute("announcement", new Announcement());
		}
		return "admin/add-edit-announcement";
	}

	@RequestMapping(path = "/delete/{id}")
	public String deleteAnnouncementById(Model model, @PathVariable("id") Long id)
			throws RecordNotFoundException {
		service.deleteAnnouncementById(id);
		return REDIRECT + VIEW;
	}

	@PostMapping(path = "/createAnnouncement")
	public String createOrUpdateAnnouncement(Announcement announcement) {
		service.createOrUpdateAnnouncement(announcement);
		return REDIRECT + VIEW;
	}
}
