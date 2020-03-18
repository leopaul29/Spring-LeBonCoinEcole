package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import java.util.List;
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
public class CategoryRepositoryIntegrationTest {
	private static final Logger logger = LoggerFactory.getLogger(CategoryRepositoryIntegrationTest.class);

	public Category deviceCategory;

	@Autowired
	private EntityManager em;
	@Autowired
	private CategoryRepository repository;

	@Before
	public void SetUp() {
		deviceCategory = new Category("Device");
		em.persist(deviceCategory);
		em.flush();
	}

	// write test cases here

	/*
	Find methods
	 */
	@Test
	public void whenFindAll_thenReturnListCategory() {
		// when
		List<Category> categories = repository.findAll();

		// then
		assertThat(categories).isNotNull().isNotEmpty().hasSize(1);
	}

	@Test
	public void whenFindById_thenReturnCategory() {
		// when
		Optional<Category> existing = repository.findById(deviceCategory.getCategoryId());

		// then
		assertThat(existing.isPresent()).isTrue();

		Category found = existing.get();
		assertThat(found.getLabel())
				.isEqualTo(deviceCategory.getLabel());
	}

	@Test
	public void whenFindById_thenNoResult() {
		// when
		Optional<Category> existing = repository.findById(100L);

		// then
		assertThat(existing.isPresent()).isFalse();
	}

	@Test
	public void whenFindByLabel_thenReturnCategory() {
		// when
		Category found = repository.findByLabel(deviceCategory.getLabel());

		// then
		assertThat(found.getLabel())
				.isEqualTo(deviceCategory.getLabel());
	}

	@Test
	public void whenFindByLabel_thenNoResult() {
		// when
		Category found = repository.findByLabel("Label not exist");

		// then
		assertThat(found).isNull();
	}

	/*
	Save methods
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void whenCategoryNoLabelSaved_thenDataIntegrityViolationException() {
		// when
		Category emptyCategory = new Category();
		repository.saveAndFlush(emptyCategory);

		// then throw DataIntegrityViolationException
	}

	@Test(expected = ConstraintViolationException.class)
	public void whenCategoryLabelUnderSizeMinSaved_thenConstraintViolationException() {
		// when
		Category emptyCategory = new Category("A");
		repository.saveAndFlush(emptyCategory);

		// then throw ConstraintViolationException
	}

	@Test(expected = ConstraintViolationException.class)
	public void whenCategoryLabelUpperSizeMaxSaved_thenConstraintViolationException() {
		// when
		Category emptyCategory = new Category("aaaaaaaaaa bbbbbbbbbb cccccccccc dddddddddd eeeeeeeeee");
		repository.saveAndFlush(emptyCategory);

		// then throw ConstraintViolationException
	}

	@Test
	public void whenCategoryLabelSaved_thenReturnCategory() {
		// when
		String label = "Books";
		Category booksCategory = new Category(label);
		Category found = repository.saveAndFlush(booksCategory);

		// then
		assertThat(found)
				.isNotNull();
		assertThat(found.getLabel())
				.isEqualTo(label);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void whenCategoryLabelSavedTwice_thenDataIntegrityViolationException() {
		// when
		String label = "Books";
		Category booksCategory = new Category(label);
		repository.saveAndFlush(booksCategory);
		Category booksCategoryCopy = new Category(label);
		repository.saveAndFlush(booksCategoryCopy);

		// then throw DataIntegrityViolationException
	}

	/*
	Update methods
	 */
	@Test
	public void whenCategoryUpdated_thenReturnCategory() {
		// when
		Category booksCategory = new Category("Books");
		Category found = repository.saveAndFlush(booksCategory);
		String label = "Comics";
		found.setLabel(label);
		Category updatedCategory = repository.saveAndFlush(found);

		// then
		assertThat(found.getLabel())
				.isEqualTo(label);
		assertThat(updatedCategory.getLabel())
				.isEqualTo(label);
		assertThat(found.getCategoryId())
				.isEqualTo(updatedCategory.getCategoryId());
	}

	/*
	Delete methods
	 */
	@Test
	public void whenCategoryDeleted_thenShouldNotFindCategory() {
		// when
		repository.delete(deviceCategory);
		Optional<Category> found = repository.findById(deviceCategory.getCategoryId());

		// then
		assertThat(found.isPresent())
				.isFalse();
	}

	@Test
	public void whenAllCategoriesDeleted_thenShouldNotFindCategory() {
		// when
		repository.deleteAll();
		List<Category> categories = repository.findAll();

		// then
		assertThat(categories)
				.isNotNull()
				.isEmpty();
	}
}
