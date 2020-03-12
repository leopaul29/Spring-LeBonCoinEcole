package com.leopaulmartin.spring.leboncoinecole.controllers.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@Value("${spring.application.name}")
	String appName;
	@Value("${app.version}")
	String appVersion;

	@GetMapping("/admin")
	public String adminHomePage(Model model) {
		model.addAttribute("appName", appName);
		model.addAttribute("appVersion", appVersion);
		return "admin/home";
	}
}
