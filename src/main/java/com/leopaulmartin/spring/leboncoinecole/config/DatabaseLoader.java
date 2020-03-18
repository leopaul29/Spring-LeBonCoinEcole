package com.leopaulmartin.spring.leboncoinecole.config;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.*;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private PhoneNumberRepository phoneNumberRepository;
	@Autowired
	private EmailRepository emailRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private SchoolRepository schoolRepository;
	@Autowired
	private AnnouncementRepository announcementRepository;

	@Override
	public void run(String... strings) {
		// Student & Phonenumber & Email
		PhoneNumber phn1 = new PhoneNumber("0123456789");
		phoneNumberRepository.saveAndFlush(phn1);
		List<PhoneNumber> listPhn1 = new ArrayList<>();
		listPhn1.add(phn1);

		Email email1 = new Email("contact@email.fr");
		emailRepository.saveAndFlush(email1);
		List<Email> listEmail = new ArrayList<>();
		listEmail.add(email1);

		Student stu1 = new Student("username", "password", "firstname", "lastname", listPhn1, listEmail);
		studentRepository.saveAndFlush(stu1);

		// School & Address
		Address adr1 = new Address("label", "12345", "city", "country");
		addressRepository.saveAndFlush(adr1);
		School sch1 = new School("School One", adr1);
		schoolRepository.saveAndFlush(sch1);

		// Add student in school
		List<Student> listStudent = new ArrayList<>();
		listStudent.add(stu1);
		sch1.setStudents(listStudent);
		schoolRepository.saveAndFlush(sch1);

		// Announcement & Categories
		// Categories
		Category cat1 = new Category("category 1");
		categoryRepository.saveAndFlush(cat1);
		// Announcement
		List<Category> listCat = new ArrayList<>();
		listCat.add(cat1);
		Announcement ann1 = new Announcement("title", "description", 10.0f);
		ann1.setCategories(listCat);
		announcementRepository.saveAndFlush(ann1);

		// Add announcement in student list
		List<Announcement> listann = new ArrayList<>();
		listann.add(ann1);
		stu1.setAnnouncements(listann);
		studentRepository.saveAndFlush(stu1);
	}
}
