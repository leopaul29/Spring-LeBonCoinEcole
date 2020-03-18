package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

	public Announcement ann1;
	public Category deviceCategory;

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private AnnouncementRepository repository;

	@Before
	public void setUp() {
		deviceCategory = new Category("Device");
		List<Category> categoryList = new ArrayList<>();
		categoryList.add(deviceCategory);

		ann1 = new Announcement("title1", "description1", 0, categoryList);

		entityManager.persist(deviceCategory);
		entityManager.persist(ann1);
		entityManager.flush();
	}

	// write test cases here
	@Test
	public void whenFindById_thenReturnAnnouncement() {
		// when
		Optional<Announcement> existing = repository.findById(ann1.getAnnouncementId());

		// then
		assertThat(existing.get()).isNotNull();
		Announcement found = existing.get();
		assertThat(found.getTitle()).isEqualTo(found.getTitle());
	}
}
