package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.PhoneNumber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PhoneNumberRepositoryIntegrationTest {
	private static final Logger logger = LoggerFactory.getLogger(PhoneNumberRepositoryIntegrationTest.class);

	public PhoneNumber phn1, phn2;

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private PhoneNumberRepository repository;

	@Before
	public void SetUp() {
		phn1 = new PhoneNumber("0123456789");
		phn2 = new PhoneNumber("9876543210");
		entityManager.persist(phn1);
		entityManager.persist(phn2);
		entityManager.flush();
	}

	// write test cases here
	@Test
	public void whenFindById_thenReturnPhoneNumber() {
		// when
		Optional<PhoneNumber> existing = repository.findById(phn1.getPhonenumberId());

		// then
		assertThat(existing.isPresent()).isTrue();
		PhoneNumber found = existing.get();
		assertThat(found.getNumber()).isEqualTo(phn1.getNumber());
	}

	@Test
	public void whenFindByNumber_thenReturnPhoneNumber() {
		// when
		PhoneNumber found = repository.findByNumber(phn2.getNumber());

		// then
		assertThat(found.getNumber()).isEqualTo(phn2.getNumber());
	}
}
