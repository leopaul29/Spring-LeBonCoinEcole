package com.leopaulmartin.spring.leboncoinecole.web.controllers;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student;
import com.leopaulmartin.spring.leboncoinecole.security.MyUserPrincipal;
import com.leopaulmartin.spring.leboncoinecole.services.*;
import com.leopaulmartin.spring.leboncoinecole.web.dto.StudentDto;
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

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AnnouncementService announcementService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private UserService userService;

	@ModelAttribute("allAnnouncements")
	public List<Announcement> populateAnnouncements() {
		return announcementService.getAllAnnouncements();
	}
	@ModelAttribute("allCategories")
	public List<Category> populateCategories() {
		return categoryService.getAllCategories();
	}
	@ModelAttribute("allSchools")
	public List<School> populateSchools() {
		return schoolService.getAllSchools();
	}
	@ModelAttribute("currentUser")
	public MyUserPrincipal getCurrentUser(Authentication authentication) {
		return (authentication == null) ? null : (MyUserPrincipal) authentication.getPrincipal();
	}
	@ModelAttribute("currentStudent")
	public Student getCurrentStudent(Authentication authentication) {
		MyUserPrincipal currentUser = getCurrentUser(authentication);
		if (currentUser!=null) {
			return studentService.getStudentByUserProfile(currentUser.getUser());
		}
		return null;
	}

	//	Dropdown Account Menu
	@GetMapping("/dashboard")
	public String showDashboard() {
		return "student/index";
	}

	@GetMapping("/edit")
	public String showEditAccount(Model model, Authentication authentication) {
		Student currentStudent = getCurrentStudent(authentication);
		StudentDto studentDto = convertToDto(currentStudent);
		model.addAttribute("studentDto", studentDto);
		return "student/edit-account";
	}

	//TODO: handle edit account request
	@PostMapping("/edit")
	public String handleEditAccountRequest(Model model) {
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

//	private Student convertToEntity(StudentDto studentDto) {
//		Student student = new Student();
//		if (studentDto.getStudentId()!=null) {
//			student = studentService.getStudentById(studentDto.getStudentId());
//		}
//		student.getUserProfile().setFirstName(studentDto.getFirstName());
//		student.getUserProfile().setLastName(studentDto.getLastName());
//		student.getUserProfile().setPassword(studentDto.getPassword());
//		student.setPhoneNumber(studentDto.getPhoneNumber());
//		student.setPhoto(studentDto.getPhoto());
//		student.setSchool(studentDto.getSchool());
//
//		return student;
//	}

	private StudentDto convertToDto(Student student) {
		StudentDto studentDto = new StudentDto();

		if (student!=null) {
//			studentDto.setStudentId(student.getStudentId());
			studentDto.setEmail(student.getUserProfile().getEmail());
			studentDto.setPassword(student.getUserProfile().getPassword());
			studentDto.setFirstName(student.getUserProfile().getFirstName());
			studentDto.setLastName(student.getUserProfile().getLastName());
			studentDto.setPhoneNumber(student.getPhoneNumber());
			studentDto.setSchool(student.getSchool());
		}

		return studentDto;
	}
}
