package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/*
https://www.baeldung.com/spring-boot-testing
https://www.baeldung.com/introduction-to-assertj
 */
/*
@RunWith(SpringRunner.class) is used to provide a bridge between Spring Boot test features and JUnit. Whenever we are using any Spring Boot testing features in our JUnit tests, this annotation will be required.
 */
@RunWith(SpringRunner.class)
/*
@DataJpaTest provides some standard setup needed for testing the persistence layer:
    configuring H2, an in-memory database
    > The H2 DB is our in-memory database. It eliminates the need for configuring and starting an actual database for test purposes.
    setting Hibernate, Spring Data, and the DataSource
    performing an @EntityScan
    turning on SQL logging
 */
@DataJpaTest
@AutoConfigureTestDatabase
public class StudentRepositoryIntegrationTest {

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private CategoryRepository repository;

	// write test cases here
	@Test
	public void whenFindById_thenReturnCategory() {
		// given
		Category device = new Category();
		device.setLabel("Device");
		entityManager.persist(device);
		entityManager.flush();

		// when
		Optional<Category> existing = repository.findById(device.getCategoryId());

		// then
		assertThat(existing.get()).isNotNull();
		Category found = existing.get();
		assertThat(found.getLabel()).isEqualTo(device.getLabel());
	}

	@Test
	public void whenFindByLabel_thenReturnCategory() {
		// given
		Category device = new Category();
		device.setLabel("Device");
		entityManager.persist(device);
		entityManager.flush();

		// when
		Category found = repository.findByLabel(device.getLabel());

		// then
		assertThat(found.getLabel()).isEqualTo(device.getLabel());
	}
}
