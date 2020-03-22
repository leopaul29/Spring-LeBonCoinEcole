package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Email;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class EmailRepositoryIntegrationTest {

	public Email email1, email2;
	public String emailPro = "pro@leboncoinecole.fr", emailContact = "contact@leboncoinecole.fr";

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private EmailRepository repository;

	@Before
	public void SetUp() {
		// given
		email1 = new Email(emailContact);
		entityManager.persist(email1);
		email2 = new Email(emailPro);
		entityManager.persist(email2);

		entityManager.flush();
	}

	// write test cases here
	/*
	Find methods
	 */
	@Test
	public void whenFindById_thenReturnEmail() {
		// when
		Email found = repository.getOne(email1.getEmailId());

		// then
		assertThat(found.getEmail()).isEqualTo(emailContact);
	}

	@Test(expected = EntityNotFoundException.class)
	public void whenFindById_thenReturnNull() {
		// when
		Long wrongId = 101L;
		Email found = repository.getOne(wrongId);

		// then throw EntityNotFoundException
		assertThat(found).isNull();
	}

	@Test
	public void whenFindByEmail_thenReturnEmail() {
		// when
		Email found = repository.findByEmail(emailPro);

		// then
		assertThat(found.getEmail()).isEqualTo(emailPro);
	}

	/*
	Save methods
	 */
	@Test(expected = ConstraintViolationException.class)
	public void whenAddressNoLabelSaved_thenDataIntegrityViolationException() {
		// when
		Email emptyAddress = new Email();
		repository.saveAndFlush(emptyAddress);

		// then throw DataIntegrityViolationException
	}

	@Test(expected = ConstraintViolationException.class)
	public void whenAddressLabelUnderSizeMinSaved_thenConstraintViolationException() {
		// when
		Email address = new Email("a");
		repository.saveAndFlush(address);

		// then throw ConstraintViolationException
	}
}
