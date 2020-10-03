package com.leopaulmartin.spring.leboncoinecole.web.controllers;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.security.MyUserPrincipal;
import com.leopaulmartin.spring.leboncoinecole.services.AnnouncementService;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import com.leopaulmartin.spring.leboncoinecole.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
	public static final String REDIRECT = "redirect:/";
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AnnouncementService announcementService;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;

	@ModelAttribute("allCategories")
	public List<Category> populateCategories() {
		return categoryService.getAllCategories();
	}
	@ModelAttribute("allAnnouncements")
	public List<Announcement> populateAnnouncements() {
		return announcementService.getAllAnnouncements();
	}

	@ModelAttribute("currentUser")
	public MyUserPrincipal getCurrentUser(Authentication authentication) {
		return (authentication == null) ? null : (MyUserPrincipal) authentication.getPrincipal();
	}

	//	Dropdown Account Menu
	@GetMapping("/dashboard")
	public String showDashboard() {
		return "student/index";
	}

	//TODO: show edit account page
	@GetMapping("/edit")
	public String showEditAccount(Model model, HttpSession session) {
//		Long myUserId = (Long) session.getAttribute("myUserId");
//		userService.findById(myUserId);
//		if()
//		model.addAttribute("myAccount", myProfile);
		return "student/edit-account";
	}

	//TODO: handle edit account request
	@PostMapping("/edit")
	public String handleEditAccountRequest(Model model, HttpSession session) {
		logger.debug("handleEditAccountRequest");
//		Long myUserId = (Long) session.getAttribute("myUserId");
//		userService.findById(myUserId);
//		if()
//		model.addAttribute("myAccount", myProfile);
		return "student/edit-account";
	}

	//TODO: show history page
	@GetMapping("/history")
	public String showHistory() {
		return "student/history";
	}

	//TODO: handle clear history request
	@PostMapping("/history/clear")
	public String handleClearHistoryRequest() {
		logger.debug("handleClearHistoryRequest");
		return "student/history";
	}
}
