package com.leopaulmartin.spring.leboncoinecole.web.controllers.admin;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student;
import com.leopaulmartin.spring.leboncoinecole.services.SchoolService;
import com.leopaulmartin.spring.leboncoinecole.services.StudentService;
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
@RequestMapping("/admin/students")
public class StudentAdminController {
	public static final String VIEW = "admin/students";
	public static final String REDIRECT = "redirect:/";
	private static final Logger logger = LoggerFactory.getLogger(StudentAdminController.class);
	@Autowired
	private StudentService service;
	@Autowired
	private UserService userService;
	@Autowired
	private SchoolService schoolService;

//	@ModelAttribute("allStudents")
//	public List<Student> populateStudents() {
//		return service.getAllStudents();
//	}

	@ModelAttribute("allSchools")
	public List<School> populateSchools() {
		return schoolService.getAllSchools();
	}

	@GetMapping
	public String showStudents(Model model) {
		//model.addAttribute("student", new Student());

		logger.debug("------service.getAllStudents():" + service.getAllStudents());
		logger.debug("------service.getAllUsers():" + userService.getAllUsers());
		model.addAttribute("allStudents", service.getAllStudents());
		return VIEW;
	}

	@GetMapping(path = {"/edit", "/edit/{id}"})
	public String editStudentById(Model model, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		if (id.isPresent()) {
			Student student = service.getStudentById(id.get());
			model.addAttribute("student", student);
		} else {
			model.addAttribute("student", new Student());
		}
		return "admin/add-edit-student";
	}

	@RequestMapping(path = "/delete/{id}")
	public String deleteStudentById(Model model, @PathVariable("id") Long id)
			throws RecordNotFoundException {
		service.deleteStudentById(id);
		return REDIRECT + VIEW;
	}

	@PostMapping(path = "/createStudent")
	public String createOrUpdateStudent(Student student) {
		service.createOrUpdateStudent(student);
		return REDIRECT + VIEW;
	}
}
