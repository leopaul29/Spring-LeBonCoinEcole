package com.leopaulmartin.spring.leboncoinecole.web.controllers;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.*;
import com.leopaulmartin.spring.leboncoinecole.security.MyUserPrincipal;
import com.leopaulmartin.spring.leboncoinecole.services.*;
import com.leopaulmartin.spring.leboncoinecole.web.dto.AccountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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
	@ModelAttribute("accountDto")
	public AccountDto accountDto(Authentication authentication) {
		Student currentStudent = getCurrentStudent(authentication);
		User currentUser = getCurrentUser(authentication).getUser();
		return convertToDto(currentUser, currentStudent);
	}

	//	Dropdown Account Menu
	@GetMapping("/dashboard")
	public String showDashboard() {
		return "student/index";
	}

	@GetMapping("/edit")
	public String showEditAccount(Model model, Authentication authentication) {
		return "student/edit-account";
	}

	//TODO: handle edit account request
	@PostMapping("/edit")
	public String handleEditAccountRequest(Authentication authentication,
										   @ModelAttribute("studentDto") @Valid AccountDto accountDto,
										   BindingResult result) {
		logger.debug("handleEditAccountRequest");
		User existing = userService.findByEmail(accountDto.getEmail());
		Student currentStudent = getCurrentStudent(authentication);
		if (currentStudent.getUserProfile().getUserId() != existing.getUserId()) {
			result.reject("user logged and user updated are different");
		} else {
			accountDto.setStudentId(currentStudent.getStudentId());
		}

		User updatedUser = userService.updateAccount(accountDto);
		if (updatedUser==null){
			result.reject("User not found");
		}
		Student updatedStudent = studentService.updateAccount(accountDto);
		if (updatedStudent==null){
			result.reject("Student not found");
		}

		if (result.hasErrors()) {
			logger.debug(result.toString());
			return "student/edit-account";
		}

		// force re-login of the user
		reloadUserDetails(userService.loadUserByUsername(updatedUser.getEmail()),updatedUser.getPassword());

		return "redirect:/account/dashboard";
	}

	private void reloadUserDetails(UserDetails principal, String password) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, password);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
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

	private AccountDto convertToDto(User user, Student student) {
		AccountDto accountDto = new AccountDto();

		if (user != null) {
			accountDto.setEmail(user.getEmail());
			accountDto.setFirstName(user.getFirstName());
			accountDto.setLastName(user.getLastName());
			accountDto.setCreated(user.getCreated());
		}

		if (student!=null) {
			accountDto.setStudentId(student.getStudentId());
			accountDto.setPhoneNumber(student.getPhoneNumber());
			accountDto.setPhoto(student.getPhoto());
			accountDto.setSchool(student.getSchool());
		}

		return accountDto;
	}
}
