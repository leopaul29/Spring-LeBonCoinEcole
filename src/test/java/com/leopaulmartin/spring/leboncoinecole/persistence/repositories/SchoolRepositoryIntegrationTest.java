package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Address;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class SchoolRepositoryIntegrationTest {
	private static final Logger logger = LoggerFactory.getLogger(SchoolRepositoryIntegrationTest.class);

	public School sch1;

	@Autowired
	private EntityManager em;
	@Autowired
	private SchoolRepository repository;

	@Before
	public void SetUp() {
		// given
		Address parisAddress = new Address("Rue du Faubourg Saint-Honoré", "75123", "Paris", "France");
		sch1 = new School("School number one", parisAddress);
		em.persist(parisAddress);
		em.persist(sch1);
		em.flush();
	}

	// write test cases here
	@Test
	public void whenFindById_thenReturnSchool() {
		// when
		Optional<School> existing = repository.findById(sch1.getSchoolId());

		// then
		assertThat(existing.isPresent()).isTrue();
		School found = existing.get();
		assertThat(found.getName()).isEqualTo(sch1.getName());
		assertThat(found.getAddress().getLabel()).isEqualTo(sch1.getAddress().getLabel());
	}

	@Test
	public void whenFindByAddressCity_thenReturnSchoolList() {
		List<School> schools = null;
		assertThat(schools).isNull();

		schools = new ArrayList<>();
		assertThat(schools).isNotNull().isEmpty();

		// when
		String city = "Paris";
		schools = repository.findByAddressCity(city);
		// then
		assertThat(schools).isNotNull().isNotEmpty().hasSize(1);
		School found = schools.get(0);
		assertThat(found.getName()).isEqualTo(sch1.getName());
		assertThat(found.getAddress().getCity()).isEqualTo(city);
	}
}
