package com.leopaulmartin.spring.leboncoinecole.web.controllers.admin;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;
import com.leopaulmartin.spring.leboncoinecole.services.SchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/admin/schools")
public class SchoolAdminController {
	public static final String VIEW = "admin/schools";
	public static final String REDIRECT = "redirect:/";
	private static final Logger logger = LoggerFactory.getLogger(SchoolAdminController.class);
	@Autowired
	private SchoolService service;

	@GetMapping
	public String showSchools(Model model) {
		model.addAttribute("allSchools", service.getAllSchools());
		return VIEW;
	}

	@GetMapping(path = {"/edit", "/edit/{id}"})
	public String editSchoolById(Model model, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		if (id.isPresent()) {
			School school = service.getSchoolById(id.get());
			model.addAttribute("school", school);
		} else {
			model.addAttribute("school", new School());
		}
		return "admin/add-edit-school";
	}

	@RequestMapping(path = "/delete/{id}")
	public String deleteSchoolById(Model model, @PathVariable("id") Long id)
			throws RecordNotFoundException {
		service.deleteSchoolById(id);
		return REDIRECT + VIEW;
	}

	@PostMapping(path = "/createSchool")
	public String createOrUpdateSchool(School school) {
		service.createOrUpdateSchool(school);
		return REDIRECT + VIEW;
	}
}
