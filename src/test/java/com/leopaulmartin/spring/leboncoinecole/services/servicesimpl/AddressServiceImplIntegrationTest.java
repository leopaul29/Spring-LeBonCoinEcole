package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Address;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.AddressRepository;
import com.leopaulmartin.spring.leboncoinecole.services.AddressService;
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
public class AddressServiceImplIntegrationTest {
	private static final Logger logger = LoggerFactory.getLogger(AddressServiceImplIntegrationTest.class);

	public Address parisAddress;
	public Long parisAddressId = 1L;
	public String parisAddressLabel = "Rue du Faubourg Saint-Honoré";
	public String parisAddressZipcode = "75123";
	public String parisAddressCity = "Paris";
	public String parisAddressCountry = "France";


	@Autowired
	private AddressService service;

	@MockBean
	private AddressRepository repository;

	@Before
	public void setUp() {
		// create tested category object
		parisAddress = new Address(parisAddressLabel, parisAddressZipcode, parisAddressCity, parisAddressCountry);
		parisAddress.setAddressId(parisAddressId);

		// mock getOne
		Mockito.when(repository.getOne(parisAddressId))
				.thenReturn(parisAddress);
		// mock findById
		Mockito.when(repository.findById(parisAddressId))
				.thenReturn(java.util.Optional.ofNullable(parisAddress));
		// mock save
		Mockito.when(repository.saveAndFlush(parisAddress))
				.thenReturn(parisAddress);
	}

	// write test cases here

	/*
	Find methods
	 */
	@Test
	public void whenGetAddressById_thenReturnAddress() {
		// when
		Address found = service.getAddressById(parisAddressId);

		// then
		ParisAddressCompare(found);
	}

	@Test
	public void whenGetAddressById_thenReturnNull() {
		// when
		Long addressId = 2L;
		Address found = service.getAddressById(addressId);

		// then
		assertThat(found).isNull();
	}

	/*
	Save methods
	 */
	@Test
	public void whenCreateAddress_thenReturnAddress() {
		// reset
		reset(repository);
		// mock save
		Mockito.when(repository.saveAndFlush(parisAddress))
				.thenReturn(parisAddress);

		// when
		Address found = service.createAddress(parisAddress);

		// then
		ParisAddressCompare(found);
	}

	/*
	Update methods
	 */
	@Test
	public void whenUpdateAddress_thenReturnAddress() {
		// when
		String newLabel = "New address";
		parisAddress.setLabel(newLabel);
		Address found = service.updateAddress(parisAddressId, parisAddress);

		assertThat(found)
				.isNotNull();
		assertThat(found.getAddressId())
				.isEqualTo(parisAddressId);
		assertThat(found.getLabel())
				.isEqualTo(newLabel);
	}

	@Test
	public void whenUpdateCategory_thenWrongId() {
		// when
		Long wrongId = 2L;
		Address found = service.updateAddress(wrongId, parisAddress);

		// then
		assertThat(found).isNull();
	}

	@Test
	public void whenUpdateCategory_thenLabelCannotBeNull() {
		// when
		parisAddress.setLabel(null);
		Address found = service.updateAddress(parisAddressId, parisAddress);

		// then
		assertThat(found).isNull();
	}

	@Test
	public void whenUpdateCategory_thenNullIdNotExist() {
		// when
		Address secondAddress = new Address();
		secondAddress.setLabel("new address");
		Long secondAddressId = 18L;
		secondAddress.setAddressId(secondAddressId);
		Address found = service.updateAddress(secondAddressId, secondAddress);

		// then
		assertThat(found).isNull();
	}


	/*
	Delete methods
	 */
	@Test
	public void whenDeleteAddress_thenNothingHappen() {
		// when
		Long wrongId = 14L;
		service.deleteAddressById(wrongId);
	}

	private void ParisAddressCompare(Address addressTested) {
		assertThat(addressTested).isNotNull();
		assertThat(addressTested.getLabel()).isEqualTo(parisAddressLabel);
		assertThat(addressTested.getZipCode()).isEqualTo(parisAddressZipcode);
		assertThat(addressTested.getCity()).isEqualTo(parisAddressCity);
		assertThat(addressTested.getCountry()).isEqualTo(parisAddressCountry);
	}

	/*
	To check the Service class, we need to have an instance of Service class created and available as a @Bean so that we can @Autowire it in our test class. This configuration is achieved by using the @TestConfiguration annotation.
	 */
	@TestConfiguration
	static class AddressServiceImplTestContextConfiguration {

		@Bean
		public AddressService addressService() {
			return new AddressServiceImpl();
		}
	}
}
