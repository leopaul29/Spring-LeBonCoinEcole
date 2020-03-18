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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class EmailRepositoryIntegrationTest {

	public Email email1, email2;

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private EmailRepository repository;

	@Before
	public void SetUp() {
		// given
		email1 = new Email("contact@leboncoinecole.fr");
		email2 = new Email("pro@leboncoinecole.fr");
		entityManager.persist(email1);
		entityManager.persist(email2);
		entityManager.flush();
	}

	// write test cases here
	@Test
	public void whenFindById_thenReturnEmail() {
		// when
		Optional<Email> existing = repository.findById(email1.getEmailId());

		// then
		assertThat(existing.isPresent()).isTrue();
		Email found = existing.get();
		assertThat(found.getEmail()).isEqualTo(email1.getEmail());
	}

	@Test
	public void whenFindByEmail_thenReturnEmail() {
		// when
		Email found = repository.findByEmail(email2.getEmail());

		// then
		assertThat(found.getEmail()).isEqualTo(email2.getEmail());
	}
}
