package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Address;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.PhoneNumber;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class StudentRepositoryIntegrationTest {
	private static final Logger logger = LoggerFactory.getLogger(StudentRepositoryIntegrationTest.class);

	@Autowired
	private EntityManager em;
	@Autowired
	private StudentRepository repository;

	private final String parisAddressLabel = "Rue du Faubourg Saint-Honoré";
	private final String parisAddressZipcode = "75004";
	private final String parisAddressCity = "Paris";
	private final String parisAddressCountry = "France";
	private final float parisAddressLongitude = 48.869931f;
	private final float parisAddressLatitude = 2.318397f;
	private final String schoolName = "First school";
	private final String stu1number = "0123456789";
	private final String stu1username = "username";
	private final String stu1password = "password";
	private Address parisAddress;
	private School school;
	private Student stu1;

	@Before
	public void SetUp() {
		// given
		parisAddress = new Address(parisAddressLabel, parisAddressZipcode, parisAddressCity, parisAddressCountry);
		parisAddress.setLongitude(parisAddressLongitude);
		parisAddress.setLatitude(parisAddressLatitude);
		em.persist(parisAddress);
		school = new School(schoolName, parisAddress);
		em.persist(school);

		PhoneNumber phn1 = new PhoneNumber(stu1number);
		em.persist(phn1);

		stu1 = new Student(stu1username, stu1password);
		List<PhoneNumber> phoneNumberList = new ArrayList<>();
		phoneNumberList.add(phn1);
		stu1.setPhonenumbers(phoneNumberList);
		stu1.setSchool(school);
		em.persist(stu1);

		em.flush();
	}

	// write test cases here
	@Test
	public void whenFindById_thenReturnStudent() {
		// when
		Optional<Student> existing = repository.findById(stu1.getStudentId());

		// then
		assertThat(existing.isPresent()).isTrue();
		Student found = existing.get();
		assertThat(found.getUsername()).isEqualTo(stu1.getUsername());
		assertThat(found.getPassword()).isEqualTo(stu1.getPassword());
	}

	@Test
	public void whenFindByUsername_thenReturnStudent() {
		// when
		Student found = repository.findByUsername(stu1username);

		// then
		assertThat(found.getUsername()).isEqualTo(stu1username);
	}

	@Test
	public void whenFindByPhonenumber_thenReturnStudent() {
		// when
		Student found = repository.findByPhonenumber(stu1number);

		// then
		assertThat(found.getUsername()).isEqualTo(stu1.getUsername());
	}

	@Test
	public void whenFindBySchool_thenReturnStudentList() {
		// when
		List<Student> students = repository.findBySchool(school.getSchoolId());

		// then
		assertThat(students)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		Student found = students.get(0);
	}
}
