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
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;

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
	public final String deviceCategoryLabel = "Device";
	private final long wrongId = 101L;
	public Category deviceCategory;
	@Autowired
	private EntityManager em;
	@Autowired
	private CategoryRepository repository;

	@Before
	public void SetUp() {
		deviceCategory = new Category(deviceCategoryLabel);
		em.persist(deviceCategory);

		em.flush();
	}

	private void DeviceCategoryCompare(Category categoryTested) {
		assertThat(categoryTested).isNotNull();
		assertThat(categoryTested.getLabel()).isEqualTo(deviceCategoryLabel);
	}

	// write test cases here

	/*
	Find methods
	 */
	@Test
	public void whenFindAll_thenReturnCategoryList() {
		// when
		List<Category> categories = repository.findAll();

		// then
		assertThat(categories)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		// then
		Category found = categories.get(0);
		DeviceCategoryCompare(found);
	}

	@Test
	public void whenFindById_thenReturnCategory() {
		// when
		Category found = repository.getOne(deviceCategory.getCategoryId());

		// then
		DeviceCategoryCompare(found);
	}

	@Test(expected = EntityNotFoundException.class)
	public void whenFindById_thenEntityNotFoundException() {
		// when
		Category found = repository.getOne(wrongId);

		// then
		assertThat(found).isNull();
	}

	@Test
	public void whenFindByLabel_thenReturnCategory() {
		// when
		Category found = repository.findByLabel(deviceCategory.getLabel());

		// then
		DeviceCategoryCompare(found);
	}

	@Test
	public void whenFindByLabel_thenReturnNull() {
		// when
		Category found = repository.findByLabel("Label not exist");

		// then
		assertThat(found).isNull();
	}

	/*
	Save methods
	 */
	@Test
	public void whenCategorySaved_thenReturnCategory() {
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

	@Test(expected = ConstraintViolationException.class)
	public void whenCategorySaved_NoLabel_thenConstraintViolationException() {
		// when
		Category newCategory = new Category("");
		repository.saveAndFlush(newCategory);

		// then throw DataIntegrityViolationException
	}

	@Test(expected = ConstraintViolationException.class)
	public void whenCategorySaved_LabelUnderSizeMin_thenConstraintViolationException() {
		// when
		Category newCategory = new Category("A");
		repository.saveAndFlush(newCategory);

		// then throw ConstraintViolationException
	}

	@Test(expected = ConstraintViolationException.class)
	public void whenCategorySaved_LabelUpperSizeMax_thenConstraintViolationException() {
		// when
		Category newCategory = new Category("aaaaaaaaaa bbbbbbbbbb cccccccccc dddddddddd eeeeeeeeee");
		repository.saveAndFlush(newCategory);

		// then throw ConstraintViolationException
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void whenCategorySaved_LabelAlreadyExist_thenDataIntegrityViolationException() {
		// when
		String label = "Books";
		Category booksCategory = new Category(label);
		repository.saveAndFlush(booksCategory);
		// save a second "Books" category
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

	@Test(expected = ConstraintViolationException.class)
	public void whenCategoryUpdate_NoLabel_thenConstraintViolationException() {
		// when
		Category found = repository.getOne(deviceCategory.getCategoryId());
		found.setLabel("");
		repository.saveAndFlush(found);

		// then throw DataIntegrityViolationException
	}

	@Test(expected = ConstraintViolationException.class)
	public void whenCategoryUpdate_LabelUnderSizeMin_thenConstraintViolationException() {
		// when
		Category found = repository.getOne(deviceCategory.getCategoryId());
		found.setLabel("A");
		repository.saveAndFlush(found);

		// then throw ConstraintViolationException
	}

	@Test(expected = ConstraintViolationException.class)
	public void whenCategoryUpdate_LabelUpperSizeMax_thenConstraintViolationException() {
		// when
		Category found = repository.getOne(deviceCategory.getCategoryId());
		found.setLabel("aaaaaaaaaa bbbbbbbbbb cccccccccc dddddddddd eeeeeeeeee");
		repository.saveAndFlush(found);

		// then throw ConstraintViolationException
	}
}
