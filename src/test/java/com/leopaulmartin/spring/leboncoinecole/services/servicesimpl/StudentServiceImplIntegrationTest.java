package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.StudentRepository;
import com.leopaulmartin.spring.leboncoinecole.services.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;

@RunWith(SpringRunner.class)
public class StudentServiceImplIntegrationTest {
	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImplIntegrationTest.class);

	public Student studentTester;
	public Long studentTesterId = 1L;
	public String studentTesterUsername = "myusername";
	public String studentTesterPassword = "mypassword";

	@Autowired
	private StudentService service;
//	@Autowired
//	private PhoneNumberService phoneNumberService;
//	@Autowired
//	private EmailService emailService;

	@MockBean
	private StudentRepository repository;
//	@MockBean
//	private PhoneNumberRepository phoneNumberRepository;
//	@MockBean
//	private EmailRepository emailRepository;

	@Before
	public void setUp() {
		// create tested category object
		studentTester = new Student(studentTesterUsername, studentTesterPassword);
//		studentTester.setStudentId(studentTesterId);

		// mock getOne
		Mockito.when(repository.getOne(studentTesterId))
				.thenReturn(studentTester);
		// mock findById
		Mockito.when(repository.findById(studentTesterId))
				.thenReturn(java.util.Optional.ofNullable(studentTester));
		// mock save
		Mockito.when(repository.saveAndFlush(studentTester))
				.thenReturn(studentTester);
	}

	// write test cases here

	/*
	Find methods
	 */
	@Test
	public void whenGetStudentById_thenReturnStudent() {
		// when
		Student found = service.getStudentById(studentTesterId);

		// then
		contactStudentTest(found);
	}

	@Test
	public void whenGetStudentById_thenReturnNull() {
		// when
		Long StudentId = 2L;
		Student found = service.getStudentById(StudentId);

		// then
		assertThat(found).isNull();
	}

	/*
	Save methods
	 */
	@Test
	public void whenCreateStudent_thenReturnStudent() {
		// reset
		reset(repository);
		// mock save
		Mockito.when(repository.saveAndFlush(studentTester))
				.thenReturn(studentTester);

		// when
		Student found = service.createOrUpdateStudent(studentTester);

		// then
		contactStudentTest(found);
	}

	@Test
	public void whenCreateStudentFull_thenReturnStudent() {
		// reset
		reset(repository);
		// add PhoneNumbers
//		PhoneNumber phoneNumber = phoneNumberService.createPhoneNumber(new PhoneNumber("0123456789"));
//		List<PhoneNumber> phoneNumbers = new ArrayList<>();
//		phoneNumbers.add(phoneNumber);
//		studentTester.setPhonenumbers(phoneNumbers);
		// add Emails
//		Email email = emailService.createEmail(new Email("perso@leboncoinecole.fr"));
//		List<Email> emails = new ArrayList<>();
//		emails.add(email);
//		studentTester.setEmails(emails);
		// mock save
		Mockito.when(repository.saveAndFlush(studentTester))
				.thenReturn(studentTester);

		// when
		Student found = service.createOrUpdateStudent(studentTester);

		// then
		assertThat(found).isNotNull();
//		assertThat(found.getStudentId())
//				.isEqualTo(studentTesterId);
//		assertThat(found.getUsername())
//				.isEqualTo(studentTesterUsername);
//		assertThat(found.getPassword())
//				.isEqualTo(studentTesterPassword);
//		assertThat(found.getPhonenumbers())
//				.isNotNull()
//				.isNotEmpty()
//				.hasSize(1);
//		assertThat(found.getEmails())
//				.isNotNull()
//				.isNotEmpty()
//				.hasSize(1);
	}

	/*
	Update methods
	 */
	@Test
	public void whenUpdateStudent_thenReturnStudent() {
		// when
		String newUsername = "new-user";
		String newPassword = "new-pass";
//		studentTester.setUsername(newUsername);
//		studentTester.setPassword(newPassword);
		Student found = service.createOrUpdateStudent(studentTester);

		// then
		assertThat(found)
				.isNotNull();
//		assertThat(found.getStudentId())
//				.isEqualTo(studentTesterId);
//		assertThat(found.getUsername())
//				.isEqualTo(newUsername);
//		assertThat(found.getPassword())
//				.isEqualTo(newPassword);
	}

	@Test
	public void whenUpdateCategory_thenWrongId() {
		// when
		Long wrongId = 2L;
		Student found = service.createOrUpdateStudent(studentTester);

		// then
		assertThat(found).isNull();
	}

	@Test
	public void whenUpdateCategory_thenLabelCannotBeNull() {
		// when
//		studentTester.setUsername(null);
//		studentTester.setPassword(null);
		Student found = service.createOrUpdateStudent(studentTester);

		// then
		assertThat(found).isNull();
	}

	@Test
	public void whenUpdateCategory_thenNullIdNotExist() {
		// when
		Student secondStudent = new Student();
//		secondStudent.setUsername("studentBis");
//		secondStudent.setPassword("studentBis");
		Long secondStudentId = 18L;
//		secondStudent.setStudentId(secondStudentId);
		Student found = service.createOrUpdateStudent(secondStudent);

		// then
		assertThat(found).isNull();
	}


	/*
	Delete methods
	 */
	@Test
	public void whenDeleteStudent_thenNothingHappen() {
		// when
		Long wrongId = 14L;
		service.deleteStudentById(wrongId);
	}

	/*
	IsValid
	 */
	@Test
	public void isStudentValid_thenReturnTrue() {
		// when
		boolean StudentValid = service.isStudentValid(studentTester);

		// then
		assertThat(StudentValid).isTrue();
	}

	@Test
	public void isStudentValid_thenReturnFalse() {
		// when
		boolean StudentValid = service.isStudentValid(new Student());

		// then
		assertThat(StudentValid).isFalse();
	}

	private void contactStudentTest(Student studentTested) {
		assertThat(studentTested).isNotNull();
//		assertThat(studentTested.getStudentId()).isEqualTo(studentTesterId);
//		assertThat(studentTested.getUsername()).isEqualTo(studentTesterUsername);
//		assertThat(studentTested.getPassword()).isEqualTo(studentTesterPassword);
//		assertThat(studentTested.getPhonenumbers()).isNullOrEmpty();
//		assertThat(studentTested.getEmails()).isNullOrEmpty();
		assertThat(studentTested.getAnnouncements()).isNullOrEmpty();
	}

	/*
	To check the Service class, we need to have an instance of Service class created and available as a @Bean so that we can @Autowire it in our test class. This configuration is achieved by using the @TestConfiguration annotation.
	 */
	@TestConfiguration
	static class StudentServiceImplTestContextConfiguration {

		@Bean
		public StudentService studentService() {
			return new StudentServiceImpl();
		}

//		@Bean
//		public PhoneNumberService phoneNumberService() {
//			return new PhoneNumberServiceImpl();
//		}
//
//		@Bean
//		public EmailService emailService() {
//			return new EmailServiceImpl();
//		}
	}
}
