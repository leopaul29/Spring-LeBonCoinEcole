package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Address;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class SchoolRepositoryIntegrationTest {
	private static final Logger logger = LoggerFactory.getLogger(SchoolRepositoryIntegrationTest.class);

	public School school;
	public String schoolName = "First school";
	public Address parisAddress;
	private String parisAddressLabel = "Rue du Faubourg Saint-Honoré";

	private Student stu1;
	private Student stu2;

	@Autowired
	private EntityManager em;
	@Autowired
	private SchoolRepository repository;

	@Before
	public void SetUp() {
		// given
		parisAddress = new Address(parisAddressLabel, "75123", "Paris", "France");
		em.persist(parisAddress);
		school = new School(schoolName, parisAddress);
		em.persist(school);

//		stu1 = new Student("username1@t", "password1");
		stu1 = new Student();
		stu1.setSchool(school);
		em.persist(stu1);
//		stu2 = new Student("username2@t", "password2");
		stu2 = new Student();
		stu2.setSchool(school);
		em.persist(stu2);

		em.flush();
	}

	// write test cases here
	@Test
	public void whenFindById_thenReturnSchool() {
		// when
		School found = repository.getOne(school.getSchoolId());

		// then
		assertThat(found.getName()).isNotNull();
		assertThat(found.getName()).isEqualTo(school.getName());
		assertThat(found.getAddress().getLabel()).isEqualTo(school.getAddress().getLabel());
	}

	@Test
	public void whenFindByAddressCity_thenReturnSchoolList() {
		// when
		String city = "Paris";
		List<School> schools = repository.findByAddressCity(city);

		// then
		assertThat(schools)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		School found = schools.get(0);
		assertThat(found.getName()).isEqualTo(school.getName());
		assertThat(found.getAddress().getCity()).isEqualTo(city);
	}

	@Test
	public void whenFindByAddressCity_thenReturnEmptySchoolList() {
		// when
		String city = "London";
		List<School> schools = repository.findByAddressCity(city);

		// then
		assertThat(schools)
				.isNotNull()
				.isEmpty();
	}
}
