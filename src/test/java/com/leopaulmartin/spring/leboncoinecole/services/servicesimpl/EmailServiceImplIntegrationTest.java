package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Email;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.EmailRepository;
import com.leopaulmartin.spring.leboncoinecole.services.EmailService;
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
public class EmailServiceImplIntegrationTest {
	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImplIntegrationTest.class);

	public Email contactEmail;
	public Long contactEmailId = 1L;
	public String contactEmailEmail = "contact@leboncoinecole.fr";

	@Autowired
	private EmailService service;

	@MockBean
	private EmailRepository repository;

	@Before
	public void setUp() {
		// create tested category object
		contactEmail = new Email(contactEmailEmail);
		contactEmail.setEmailId(contactEmailId);

		// mock getOne
		Mockito.when(repository.getOne(contactEmailId))
				.thenReturn(contactEmail);
		// mock findById
		Mockito.when(repository.findById(contactEmailId))
				.thenReturn(java.util.Optional.ofNullable(contactEmail));
		// mock save
		Mockito.when(repository.saveAndFlush(contactEmail))
				.thenReturn(contactEmail);
	}

	// write test cases here

	/*
	Find methods
	 */
	@Test
	public void whenGetEmailById_thenReturnEmail() {
		// when
		Email found = service.getEmailById(contactEmailId);

		// then
		contactEmailTest(found);
	}

	@Test
	public void whenGetEmailById_thenReturnNull() {
		// when
		Long EmailId = 2L;
		Email found = service.getEmailById(EmailId);

		// then
		assertThat(found).isNull();
	}

	/*
	Save methods
	 */
	@Test
	public void whenCreateEmail_thenReturnEmail() {
		// reset
		reset(repository);
		// mock save
		Mockito.when(repository.saveAndFlush(contactEmail))
				.thenReturn(contactEmail);

		// when
		Email found = service.createEmail(contactEmail);

		// then
		contactEmailTest(found);
	}

	/*
	Update methods
	 */
	@Test
	public void whenUpdateEmail_thenReturnEmail() {
		// when
		String newEmail = "new-email";
		contactEmail.setEmail(newEmail);
		Email found = service.updateEmail(contactEmailId, contactEmail);

		// then
		assertThat(found)
				.isNotNull();
		assertThat(found.getEmailId())
				.isEqualTo(contactEmailId);
		assertThat(found.getEmail())
				.isEqualTo(newEmail);
	}

	@Test
	public void whenUpdateCategory_thenWrongId() {
		// when
		Long wrongId = 2L;
		Email found = service.updateEmail(wrongId, contactEmail);

		// then
		assertThat(found).isNull();
	}

	@Test
	public void whenUpdateCategory_thenLabelCannotBeNull() {
		// when
		contactEmail.setEmail(null);
		Email found = service.updateEmail(contactEmailId, contactEmail);

		// then
		assertThat(found).isNull();
	}

	@Test
	public void whenUpdateCategory_thenNullIdNotExist() {
		// when
		Email secondEmail = new Email();
		secondEmail.setEmail("new-email@leboncoinecole.fr");
		Long secondEmailId = 18L;
		secondEmail.setEmailId(secondEmailId);
		Email found = service.updateEmail(secondEmailId, secondEmail);

		// then
		assertThat(found).isNull();
	}


	/*
	Delete methods
	 */
	@Test
	public void whenDeleteEmail_thenNothingHappen() {
		// when
		Long wrongId = 14L;
		service.deleteEmailById(wrongId);
	}

	/*
	IsValid
	 */
	@Test
	public void isEmailValid_thenReturnTrue() {
		// when
		boolean EmailValid = service.isEmailValid(contactEmail);

		// then
		assertThat(EmailValid).isTrue();
	}

	@Test
	public void isEmailValid_thenReturnFalse() {
		// when
		boolean EmailValid = service.isEmailValid(new Email());

		// then
		assertThat(EmailValid).isFalse();
	}

	private void contactEmailTest(Email emailTested) {
		assertThat(emailTested).isNotNull();
		assertThat(emailTested.getEmailId()).isEqualTo(contactEmailId);
		assertThat(emailTested.getEmail()).isEqualTo(contactEmailEmail);
	}

	/*
	To check the Service class, we need to have an instance of Service class created and available as a @Bean so that we can @Autowire it in our test class. This configuration is achieved by using the @TestConfiguration annotation.
	 */
	@TestConfiguration
	static class EmailServiceImplTestContextConfiguration {

		@Bean
		public EmailService emailService() {
			return new EmailServiceImpl();
		}
	}
}
