package com.leopaulmartin.spring.leboncoinecole.web.controllers;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.services.AnnouncementService;
import com.leopaulmartin.spring.leboncoinecole.web.dto.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class AnnounceController {
	public static final String REDIRECT = "redirect:/";
	private static final Logger logger = LoggerFactory.getLogger(AnnounceController.class);
	@Autowired
	private AnnouncementService announcementService;

	// request GET sample: https://.../recherche/?category=43&text=nitendo&locations=Lyon__45.76071825414269_4.836251707178035_7600&search_in=subject
	@GetMapping("/search")
	public String handleSearchRequest(@ModelAttribute("searchForm") SearchForm searchForm, BindingResult errors, HttpServletRequest request, Model model) {
		//TODO: foreach announce: display title, category, school and creation date
		logger.debug("searchForm:" + searchForm.toString());

		List<Announcement> allAnnouncements = announcementService.getAllAnnouncements();
		logger.debug(allAnnouncements.toString());
		model.addAttribute("allAnnouncements", allAnnouncements);
		return "search";
	}

	//TODO: use AJAX to propose words for the keyword field (with elastic search ?)
	//TODO: save the research

	@GetMapping(path = {"/create-announce", "/edit-announce/{id}"})
	public String editAnnouncementById(Model model, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		if (id.isPresent()) {
			Announcement announcement = announcementService.getAnnouncementById(id.get());
			model.addAttribute("announcement", announcement);
		} else {
			model.addAttribute("announcement", new Announcement());
		}
		return "add-edit-announcement";
	}

	@GetMapping(path = {"/announces/view/{id}"})
	public String viewAnnouncementById(Model model, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		if (id.isPresent()) {
			Announcement announcement = announcementService.getAnnouncementById(id.get());
			model.addAttribute("announcement", announcement);
			return "display-one";
		} else {
			return "error-404";
		}
	}


}
