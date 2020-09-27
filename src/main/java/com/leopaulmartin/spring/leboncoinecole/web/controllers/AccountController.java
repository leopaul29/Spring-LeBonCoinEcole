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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {
	public static final String REDIRECT = "redirect:/";
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
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
	@GetMapping("/dashboard")
	public String account() {
		return "student/index";
	}

	//TODO: edit account data
	@GetMapping("/edit")
	public String editAccount(Model model, HttpSession session) {
//		Long myUserId = (Long) session.getAttribute("myUserId");
//		userService.findById(myUserId);
//		if()
//		model.addAttribute("myAccount", myProfile);
		return "student/edit-account";
	}

	//TODO: check announced previously payed
	@GetMapping("/history")
	public String showHistory() {
		return "student/history";
	}

	@PostMapping("/history/delete")
	public String handleDeleteHistoricRequest() {
		logger.debug("handleDeleteHistoricRequest");
		return "student/history";
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
