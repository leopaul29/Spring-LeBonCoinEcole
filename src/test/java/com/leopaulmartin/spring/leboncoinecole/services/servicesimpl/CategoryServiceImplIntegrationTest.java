package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordAlreadyExistException;
import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordIdMismatchException;
import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.CategoryRepository;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
https://www.baeldung.com/spring-boot-testing
 */
@RunWith(SpringRunner.class)
public class CategoryServiceImplIntegrationTest {
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImplIntegrationTest.class);

	public Category deviceCategory;
	public Long deviceCategoryId = 1L;
	public String deviceCategoryLabel = "Devices";

	@Autowired
	private CategoryService service;
	/*
	@MockBean. It creates a Mock for the EmployeeRepository which can be used to bypass the call to the actual CategoryRepository
	 */
	@MockBean
	private CategoryRepository repository;

	@Before
	public void setUp() {
		Mockito.when(repository.findAll())
				.thenReturn(new ArrayList<>());

		deviceCategory = new Category(deviceCategoryLabel);
		deviceCategory.setCategoryId(deviceCategoryId);

		Mockito.when(repository.findByLabel(deviceCategoryLabel))
				.thenReturn(deviceCategory);

		Mockito.when(repository.findById(deviceCategoryId))
				.thenReturn(java.util.Optional.ofNullable(deviceCategory));

		Mockito.when(repository.saveAndFlush(deviceCategory))
				.thenReturn(deviceCategory);
	}

	// write test cases here

	/*
	Find methods
	 */
	@Test
	public void whenGetAllCategories_thenEmptyList() {
		// when
		List<Category> categories = service.getAllCategories();

		// then
		assertThat(categories)
				.isNotNull()
				.isEmpty();
	}

	@Test
	public void whenGetCategoryById_thenReturnCategory() {
		// when
		Category found = service.getCategoryById(deviceCategoryId);

		// then
		assertThat(found.getLabel())
				.isEqualTo(deviceCategoryLabel);
	}

	@Test(expected = RecordNotFoundException.class)
	public void whenGetCategoryById_thenRecordNotFoundException() {
		// when
		Long categoryId = 2L;
		Category found = service.getCategoryById(categoryId);

		// then throw RecordNotFoundException
	}

	@Test
	public void whenGetCategoryByLabel_thenReturnCategory() {
		// when
		Category found = service.getCategoryByLabel(deviceCategoryLabel);

		// then
		assertThat(found.getLabel())
				.isEqualTo(deviceCategoryLabel);
	}

	@Test(expected = RecordNotFoundException.class)
	public void whenGetCategoryByLabel_thenRecordNotFoundException() {
		// when
		String label = "TV";
		Category found = service.getCategoryByLabel(label);

		// then throw RecordNotFoundException
	}

	/*
	Save methods
	 */
	@Test(expected = RecordAlreadyExistException.class)
	public void whenCreateCategory_thenRecordAlreadyExistException() {
		// when
		Category found = service.createCategory(deviceCategory);

		// then throw RecordAlreadyExistException
	}

	/*
	Update methods
	 */
	@Test
	public void whenUpdateCategory_thenReturnCategory() {
		// when
		String newLabel = "New devices";
		deviceCategory.setLabel(newLabel);
		Category found = service.updateCategory(deviceCategoryId, deviceCategory);

		assertThat(found)
				.isNotNull();
		assertThat(found.getLabel())
				.isEqualTo(newLabel);
	}

	@Test(expected = RecordIdMismatchException.class)
	public void whenUpdateCategory_thenRecordIdMismatchException() {
		// when
		Category found = service.updateCategory(2L, deviceCategory);

		// then throw RecordIdMismatchException
	}

	@Test(expected = RecordAlreadyExistException.class)
	public void whenUpdateCategory_thenRecordAlreadyExistException() {
		// when
		Category found = service.updateCategory(deviceCategoryId, deviceCategory);

		// then throw RecordAlreadyExistException
	}

	@Test(expected = RecordNotFoundException.class)
	public void whenUpdateCategory_thenRecordNotFoundException() {
		// when
		Category tvCategory = new Category("TV");
		Long tvCategoryId = 18L;
		tvCategory.setCategoryId(tvCategoryId);
		Category found = service.updateCategory(tvCategoryId, tvCategory);

		// then throw RecordAlreadyExistException
	}

	/*
	Delete methods
	 */
	@Test(expected = RecordNotFoundException.class)
	public void whenDeleteCategory_thenRecordNotFoundException() {
		// when
		service.deleteCategoryById(14L);
	}


	/*
	To check the Service class, we need to have an instance of Service class created and available as a @Bean so that we can @Autowire it in our test class. This configuration is achieved by using the @TestConfiguration annotation.
	 */
	@TestConfiguration
	static class CategoryServiceImplTestContextConfiguration {

		@Bean
		public CategoryService employeeService() {
			return new CategoryServiceImpl();
		}
	}
}
