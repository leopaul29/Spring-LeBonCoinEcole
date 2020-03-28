package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class AdministratorRepositoryIntegrationTest {

	public Administrator adm1, adm2;

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private AdministratorRepository repository;

	@Before
	public void setUp() {
		// given
		adm1 = new Administrator("user1", "pass1");
		adm2 = new Administrator("user2", "pass2");
		entityManager.persist(adm1);
		entityManager.persist(adm2);
		entityManager.flush();
	}

	// write test cases here
	@Test
	public void whenFindById_thenReturnAdministrator() {
		// when
//		Optional<Administrator> existing1 = repository.findById(adm1.getAdministratorId());
//		Optional<Administrator> existing2 = repository.findById(adm2.getAdministratorId());

		// then
//		assertThat(existing1.get()).isNotNull();
//		Administrator found1 = existing1.get();
//		assertThat(found1.getUsername()).isEqualTo(found1.getUsername());
//
//		assertThat(existing2.get()).isNotNull();
//		Administrator found2 = existing2.get();
//		assertThat(found2.getUsername()).isEqualTo(found2.getUsername());
	}
}
