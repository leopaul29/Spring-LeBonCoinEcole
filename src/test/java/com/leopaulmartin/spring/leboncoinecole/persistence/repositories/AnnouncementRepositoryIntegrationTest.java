package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class AnnouncementRepositoryIntegrationTest {
	private static final Logger logger = LoggerFactory.getLogger(AnnouncementRepositoryIntegrationTest.class);

	public Announcement iphoneAnnounce;
	public Announcement ipadAnnounce;
	public Category deviceCategory;
	public Student student;

	@Autowired
	private EntityManager em;
	@Autowired
	private AnnouncementRepository repository;

	@Before
	public void setUp() {
		deviceCategory = new Category("Devices");
		em.persist(deviceCategory);

		List<Category> categories = new ArrayList<>();
		categories.add(deviceCategory);
		iphoneAnnounce = new Announcement("IPhone", "Super téléphone portable", 500);
		iphoneAnnounce.setCategories(categories);
		em.persist(iphoneAnnounce);
		ipadAnnounce = new Announcement("IPad", "Super tablette", 650);
		ipadAnnounce.setCategories(categories);
		em.persist(ipadAnnounce);

		List<Announcement> announcements = new ArrayList<>();
		announcements.add(iphoneAnnounce);
		announcements.add(ipadAnnounce);
		student = new Student("username", "password");
		student.setAnnouncements(announcements);
		em.persist(student);

		em.flush();
	}

	// write test cases here
	@Test
	public void whenFindById_thenReturnAnnouncement() {
		// when
		Optional<Announcement> existing = repository.findById(iphoneAnnounce.getAnnouncementId());

		// then
		assertThat(existing.isPresent()).isTrue();

		Announcement found = existing.get();
		assertThat(found.getTitle()).isEqualTo(iphoneAnnounce.getTitle());
		assertThat(found.getDescription()).isEqualTo(iphoneAnnounce.getDescription());
		assertThat(found.getPrice()).isEqualTo(iphoneAnnounce.getPrice());
		assertThat(found.getCategories()).isNotNull().isNotEmpty().hasSize(1);
	}

	@Test
	public void whenFindAnnouncementsByCategory_thenReturnAnnouncements() {
		// when
		List<Announcement> existing = repository.findAnnouncementsByCategory("Devices");

		// then
		assertThat(existing).isNotNull().isNotEmpty().hasSize(2);
	}
}
