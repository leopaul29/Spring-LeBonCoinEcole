package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.PhoneNumber;
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

	public Student stu1;
	@Autowired
	private EntityManager em;
	@Autowired
	private StudentRepository repository;

	@Before
	public void SetUp() {
		PhoneNumber phn1 = new PhoneNumber("0123456789");
		em.persist(phn1);

		stu1 = new Student("username", "password");
		List<PhoneNumber> phoneNumberList = new ArrayList<>();
		phoneNumberList.add(phn1);
		stu1.setPhonenumbers(phoneNumberList);

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
		Student found = repository.findByUsername(stu1.getUsername());

		// then
		assertThat(found.getUsername()).isEqualTo(stu1.getUsername());
	}

	@Test
	public void whenFindByPhonenumber_thenReturnStudent() {
		// when
		Student found = repository.findByPhonenumber("0123456789");

		// then
		assertThat(found.getUsername()).isEqualTo(stu1.getUsername());
	}
}
