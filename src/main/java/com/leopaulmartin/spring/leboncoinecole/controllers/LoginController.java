package com.leopaulmartin.spring.leboncoinecole.controllers;

import com.leopaulmartin.spring.leboncoinecole.forms.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@PostMapping("/login")
	public String handleLoginRequest(
			@ModelAttribute("login") LoginForm loginForm, BindingResult errors, HttpServletRequest request, Model model) {
		logger.info("logged in");

		model.addAttribute("name", loginForm.username);

		return "redirect:/home";
	}
}
