package com.leopaulmartin.spring.leboncoinecole.web.controllers;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.services.AnnouncementService;
import com.leopaulmartin.spring.leboncoinecole.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
	public static final String REDIRECT = "redirect:/";
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	private AnnouncementService announcementService;
	@Autowired
	private UserService userService;

	@ModelAttribute("allAnnouncements")
	public List<Announcement> populateAnnouncements() {
		return announcementService.getAllAnnouncements();
	}

	//	Dropdown Account Menu
	//TODO: display account data
	@GetMapping("/my-account")
	public String account() {
		return "student/index";
	}

	//TODO: edit account data
	@GetMapping("/my-account/edit")
	public String editAccount(Model model, HttpSession session) {
//		Long myUserId = (Long) session.getAttribute("myUserId");
//		userService.findById(myUserId);
//		if()
//		model.addAttribute("myAccount", myProfile);
		return "student/edit-account";
	}

	//TODO: check announced previously payed
	@GetMapping("/history")
	public String displayHistory() {
		return "student/history";
	}

	//TODO: student's announce posted
	@GetMapping("/my-announces")
	public String myAnnounces() {
		return "student/my-announces";
	}

	//	Other link in menu

	//TODO: replay a search saved
	@GetMapping("/my-searches")
	public String mySearches() {
		return "student/my-searches";
	}

	//TODO: clean my-searches
	@GetMapping("/my-searches/clear")
	public String clearMySearches() {
		// delete all history
		return REDIRECT + "student/my-searches";
	}

	//TODO: student's afavorite nnounce
	@GetMapping("/my-favorites")
	public String favorite() {
		return "student/my-favorites";
	}

	//TODO: add announce to favorite list
	@GetMapping("/my-favorites/add/{id}")
	public void addFavorite(Model model, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		if (id.isPresent()) {
			Announcement announcement = announcementService.getAnnouncementById(id.get());
//				model.addAttribute("announcement", announcement);
		}
//			return "student/my-favorites";
	}
	//TODO: delete announce to favorite list

	//TODO: check message send and received for an announce
	@GetMapping("/messages")
	public String messages() {
		return "student/messages";
	}
}
