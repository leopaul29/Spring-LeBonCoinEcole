package com.leopaulmartin.spring.leboncoinecole.repository;

import com.leopaulmartin.spring.leboncoinecole.domain.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.domain.repositories.CategoryRepository;
import com.leopaulmartin.spring.leboncoinecole.domain.services.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryIT {
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;

	@Test
	public void whenFindById_thenReturnCategory() {
		// given
		Category voitureTest = new Category();
		voitureTest.setId(10001l);
		voitureTest.setLabel("VoitureTest");
		categoryRepository.saveAndFlush(voitureTest);

		// when
		Optional<Category> found = categoryRepository.findById(10001l);

		// then
		Assert.assertTrue(found.isPresent());
		assertThat(voitureTest.getLabel()).isEqualTo(found.get().getLabel());
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void givenFooHasNoName_whenInvalidEntityIsCreated_thenDataException() throws Exception {
		categoryService.createCategory(new Category());
	}
}
