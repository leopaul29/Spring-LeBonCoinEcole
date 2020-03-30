package com.leopaulmartin.spring.leboncoinecole.web.controllers.admin;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.User;
import com.leopaulmartin.spring.leboncoinecole.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
public class UserAdminController {
	private static final Logger logger = LoggerFactory.getLogger(UserAdminController.class);
	private static final String VIEW = "admin/users";
	private static final String REDIRECT = "redirect:/";
	@Autowired
	private UserService service;

	@ModelAttribute("allUsers")
	public List<User> populateUsers() {
		return service.getAllUsers();
	}

	@GetMapping
	public String showUsers(Model model) {
		return VIEW;
	}

	@GetMapping(path = {"/edit", "/edit/{id}"})
	public String editUserById(Model model, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		if (id.isPresent()) {
			User user = service.getUserById(id.get());
			model.addAttribute("user", user);
		} else {
			model.addAttribute("user", new User());
		}
		return "admin/add-edit-user";
	}

	@RequestMapping(path = "/delete/{id}")
	public String deleteUserById(Model model, @PathVariable("id") Long id)
			throws RecordNotFoundException {
		service.deleteUserById(id);
		return REDIRECT + VIEW;
	}

	@PostMapping(path = "/createUser")
	public String createOrUpdateUser(User user) {
		service.createOrUpdateUser(user);
		return REDIRECT + VIEW;
	}
}
