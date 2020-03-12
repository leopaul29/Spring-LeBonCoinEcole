package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.CategoryRepository;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/*
https://www.baeldung.com/spring-boot-testing
 */
@RunWith(SpringRunner.class)
public class CategoryServiceImplIntegrationTest {

	@Autowired
	private CategoryService service;
	/*
	@MockBean. It creates a Mock for the EmployeeRepository which can be used to bypass the call to the actual CategoryRepository
	 */
	@MockBean
	private CategoryRepository repository;

	@Before
	public void setUp() {
		Category device = new Category("device");

		Mockito.when(repository.findByLabel(device.getLabel()))
				.thenReturn(device);
	}

	// write test cases here
	@Test
	public void whenValidName_thenEmployeeShouldBeFound() {
		String label = "device";
		Category found = service.getCategoryByLabel(label);

		assertThat(found.getLabel())
				.isEqualTo(label);
	}


	@Test(expected = DataIntegrityViolationException.class)
	public void givenFooHasNoName_whenInvalidEntityIsCreated_thenDataException() {
		service.createCategory(new Category());
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
