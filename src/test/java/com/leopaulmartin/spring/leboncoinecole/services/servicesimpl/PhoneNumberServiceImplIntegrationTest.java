package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.PhoneNumber;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.PhoneNumberRepository;
import com.leopaulmartin.spring.leboncoinecole.services.PhoneNumberService;
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
public class PhoneNumberServiceImplIntegrationTest {
	private static final Logger logger = LoggerFactory.getLogger(PhoneNumberServiceImplIntegrationTest.class);

	public PhoneNumber contactPhoneNumber;
	public Long contactPhoneNumberId = 1L;
	public String contactPhoneNumberNumber = "0123456789";

	@Autowired
	private PhoneNumberService service;

	@MockBean
	private PhoneNumberRepository repository;

	@Before
	public void setUp() {
		// create tested category object
		contactPhoneNumber = new PhoneNumber(contactPhoneNumberNumber);
		contactPhoneNumber.setPhonenumberId(contactPhoneNumberId);

		// mock getOne
		Mockito.when(repository.getOne(contactPhoneNumberId))
				.thenReturn(contactPhoneNumber);
		// mock findById
		Mockito.when(repository.findById(contactPhoneNumberId))
				.thenReturn(java.util.Optional.ofNullable(contactPhoneNumber));
		// mock save
		Mockito.when(repository.saveAndFlush(contactPhoneNumber))
				.thenReturn(contactPhoneNumber);
	}

	// write test cases here

	/*
	Find methods
	 */
	@Test
	public void whenGetPhoneNumberById_thenReturnPhoneNumber() {
		// when
		PhoneNumber found = service.getPhoneNumberById(contactPhoneNumberId);

		// then
		contactPhoneNumberTest(found);
	}

	@Test
	public void whenGetPhoneNumberById_thenReturnNull() {
		// when
		Long PhoneNumberId = 2L;
		PhoneNumber found = service.getPhoneNumberById(PhoneNumberId);

		// then
		assertThat(found).isNull();
	}

	/*
	Save methods
	 */
	@Test
	public void whenCreatePhoneNumber_thenReturnPhoneNumber() {
		// reset
		reset(repository);
		// mock save
		Mockito.when(repository.saveAndFlush(contactPhoneNumber))
				.thenReturn(contactPhoneNumber);

		// when
		PhoneNumber found = service.createPhoneNumber(contactPhoneNumber);

		// then
		contactPhoneNumberTest(found);
	}

	/*
	Update methods
	 */
	@Test
	public void whenUpdatePhoneNumber_thenReturnPhoneNumber() {
		// when
		String newPhoneNumber = "9876543210";
		contactPhoneNumber.setNumber(newPhoneNumber);
		PhoneNumber found = service.updatePhoneNumber(contactPhoneNumberId, contactPhoneNumber);

		// then
		assertThat(found)
				.isNotNull();
		assertThat(found.getPhonenumberId())
				.isEqualTo(contactPhoneNumberId);
		assertThat(found.getNumber())
				.isEqualTo(newPhoneNumber);
	}

	@Test
	public void whenUpdateCategory_thenWrongId() {
		// when
		Long wrongId = 2L;
		PhoneNumber found = service.updatePhoneNumber(wrongId, contactPhoneNumber);

		// then
		assertThat(found).isNull();
	}

	@Test
	public void whenUpdateCategory_thenLabelCannotBeNull() {
		// when
		contactPhoneNumber.setNumber(null);
		PhoneNumber found = service.updatePhoneNumber(contactPhoneNumberId, contactPhoneNumber);

		// then
		assertThat(found).isNull();
	}

	@Test
	public void whenUpdateCategory_thenNullIdNotExist() {
		// when
		PhoneNumber secondPhoneNumber = new PhoneNumber();
		secondPhoneNumber.setNumber("new-phoneNumber@leboncoinecole.fr");
		Long secondPhoneNumberId = 18L;
		secondPhoneNumber.setPhonenumberId(secondPhoneNumberId);
		PhoneNumber found = service.updatePhoneNumber(secondPhoneNumberId, secondPhoneNumber);

		// then
		assertThat(found).isNull();
	}


	/*
	Delete methods
	 */
	@Test
	public void whenDeletePhoneNumber_thenNothingHappen() {
		// when
		Long wrongId = 14L;
		service.deletePhoneNumberById(wrongId);
	}

	/*
	IsValid
	 */
	@Test
	public void isPhoneNumberValid_thenReturnTrue() {
		// when
		boolean PhoneNumberValid = service.isPhoneNumberValid(contactPhoneNumber);

		// then
		assertThat(PhoneNumberValid).isTrue();
	}

	@Test
	public void isPhoneNumberValid_thenReturnFalse() {
		// when
		boolean PhoneNumberValid = service.isPhoneNumberValid(new PhoneNumber());

		// then
		assertThat(PhoneNumberValid).isFalse();
	}

	private void contactPhoneNumberTest(PhoneNumber phoneNumberTested) {
		assertThat(phoneNumberTested).isNotNull();
		assertThat(phoneNumberTested.getPhonenumberId()).isEqualTo(contactPhoneNumberId);
		assertThat(phoneNumberTested.getNumber()).isEqualTo(contactPhoneNumberNumber);
	}

	/*
	To check the Service class, we need to have an instance of Service class created and available as a @Bean so that we can @Autowire it in our test class. This configuration is achieved by using the @TestConfiguration annotation.
	 */
	@TestConfiguration
	static class PhoneNumberServiceImplTestContextConfiguration {

		@Bean
		public PhoneNumberService phoneNumberService() {
			return new PhoneNumberServiceImpl();
		}
	}
}
