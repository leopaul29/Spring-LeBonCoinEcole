package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

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
import static org.mockito.Mockito.reset;

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
		// create tested category object
		deviceCategory = new Category(deviceCategoryLabel);
		deviceCategory.setCategoryId(deviceCategoryId);

		// mock findAll
		List<Category> categories = new ArrayList<>();
		categories.add(deviceCategory);
		Mockito.when(repository.findAll())
				.thenReturn(categories);
		// mock getOne
		Mockito.when(repository.getOne(deviceCategoryId))
				.thenReturn(deviceCategory);
		// mock findById
		Mockito.when(repository.findById(deviceCategoryId))
				.thenReturn(java.util.Optional.ofNullable(deviceCategory));
		// mock findByLabel
		Mockito.when(repository.findByLabel(deviceCategoryLabel))
				.thenReturn(deviceCategory);
		// mock save
		Mockito.when(repository.saveAndFlush(deviceCategory))
				.thenReturn(deviceCategory);
	}

	// write test cases here

	/*
	Find methods
	 */
	@Test
	public void whenGetAllCategories_thenReturnList() {
		// when
		List<Category> serviceAllCategories = service.getAllCategories();

		// then
		assertThat(serviceAllCategories)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);
	}

	@Test
	public void whenGetAllCategories_thenReturnEmptyList() {
		// reset
		reset(repository);

		// when
		List<Category> serviceAllCategories = service.getAllCategories();

		// then
		assertThat(serviceAllCategories)
				.isNotNull()
				.isEmpty();
	}

	@Test
	public void whenGetCategoryById_thenReturnCategory() {
		// when
		Category found = service.getCategoryById(deviceCategoryId);

		// then
		assertThat(found.getCategoryId())
				.isEqualTo(deviceCategoryId);
		assertThat(found.getLabel())
				.isEqualTo(deviceCategoryLabel);
	}

	@Test
	public void whenGetCategoryById_thenReturnNull() {
		// when
		Long categoryId = 2L;
		Category found = service.getCategoryById(categoryId);

		// then
		assertThat(found).isNull();
	}

	@Test
	public void whenGetCategoryByLabel_thenReturnCategory() {
		// when
		Category found = service.getCategoryByLabel(deviceCategoryLabel);

		// then
		assertThat(found.getCategoryId())
				.isEqualTo(deviceCategoryId);
		assertThat(found.getLabel())
				.isEqualTo(deviceCategoryLabel);
	}

	@Test
	public void whenGetCategoryByLabel_thenReturnNull() {
		// when
		String label = "TV";
		Category found = service.getCategoryByLabel(label);

		// then
		assertThat(found).isNull();
	}

	/*
	Save methods
	 */
	@Test
	public void whenCreateCategory_thenReturnCategory() {
		// reset findByLabel
		reset(repository);
		// mock save
		Mockito.when(repository.saveAndFlush(deviceCategory))
				.thenReturn(deviceCategory);

		// when
		Category found = service.createCategory(deviceCategory);

		// then
		assertThat(found)
				.isNotNull();
		assertThat(found.getLabel())
				.isEqualTo(deviceCategory.getLabel());
	}

	@Test
	public void whenCreateCategory_thenReturnNull() {
		// reset
		reset(repository);

		// when
		Category found = service.createCategory(deviceCategory);

		// then
		assertThat(found).isNull();
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
		assertThat(found.getCategoryId())
				.isEqualTo(deviceCategoryId);
		assertThat(found.getLabel())
				.isEqualTo(newLabel);
	}

	@Test
	public void whenUpdateCategory_thenWrongId() {
		// when
		Long wrongId = 2L;
		Category found = service.updateCategory(wrongId, deviceCategory);

		// then
		assertThat(found).isNull();
	}

	@Test
	public void whenUpdateCategory_thenAlreadyExist() {
		// when
		Category found = service.updateCategory(deviceCategoryId, deviceCategory);

		// then
		assertThat(found).isNull();
	}

	@Test
	public void whenUpdateCategory_thenNullIdNotExist() {
		// when
		Category tvCategory = new Category("TV");
		Long tvCategoryId = 18L;
		tvCategory.setCategoryId(tvCategoryId);
		Category found = service.updateCategory(tvCategoryId, tvCategory);

		// then
		assertThat(found).isNull();
	}

	/*
	Delete methods
	 */
	@Test
	public void whenDeleteCategory_thenNothingHappen() {
		// when
		Long wrongId = 14L;
		service.deleteCategoryById(wrongId);
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
