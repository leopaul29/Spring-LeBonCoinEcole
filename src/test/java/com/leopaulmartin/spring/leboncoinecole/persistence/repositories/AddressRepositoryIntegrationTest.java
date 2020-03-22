package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Address;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class AddressRepositoryIntegrationTest {
	private static final Logger logger = LoggerFactory.getLogger(AddressRepositoryIntegrationTest.class);
	private final String secondParisAddressLabel = "Boulevard Henri IV";
	private final String parisAddressLabel = "Rue du Faubourg Saint-Honoré";
	private final String parisAddressZipcode = "75004";
	private final String parisAddressCity = "Paris";
	private final String parisAddressCountry = "France";
	private final float parisAddressLongitude = 48.869931f;
	private final float parisAddressLatitude = 2.318397f;
	private final Long wrongId = 101L;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private AddressRepository repository;
	private Address parisAddress;

	@Before
	public void SetUp() {
		// given
		parisAddress = new Address(parisAddressLabel, parisAddressZipcode, parisAddressCity, parisAddressCountry);
		parisAddress.setLongitude(parisAddressLongitude);
		parisAddress.setLatitude(parisAddressLatitude);
		entityManager.persist(parisAddress);

		entityManager.flush();
	}

	private void ParisAddressCompare(Address addressTested) {
		assertThat(addressTested).isNotNull();
		assertThat(addressTested.getLabel()).isEqualTo(parisAddressLabel);
		assertThat(addressTested.getZipCode()).isEqualTo(parisAddressZipcode);
		assertThat(addressTested.getCity()).isEqualTo(parisAddressCity);
		assertThat(addressTested.getCountry()).isEqualTo(parisAddressCountry);
		assertThat(addressTested.getLongitude()).isEqualTo(parisAddressLongitude);
		assertThat(addressTested.getLatitude()).isEqualTo(parisAddressLatitude);
	}

	// write test cases here
	/*
	Find methods
	 */
	@Test
	public void whenFindById_thenReturnListAddress() {
		// when
		Address found = repository.getOne(parisAddress.getAddressId());

		// then
		ParisAddressCompare(found);
	}

	@Test(expected = EntityNotFoundException.class)
	public void whenFindById_thenEntityNotFoundException() {
		// when
		Address found = repository.getOne(wrongId);

		// then throw EntityNotFoundException
		assertThat(found).isNull();
	}

	@Test
	public void whenFindByCity_thenReturnAddressList() {
		// when
		List<Address> addresses = repository.findByCity(parisAddressCity);

		// then
		assertThat(addresses)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		Address found = addresses.get(0);
		ParisAddressCompare(found);
	}

	@Test
	public void whenFindByCity_thenReturnEmptyAddressList() {
		// when
		String wrongCity = "Nice";
		List<Address> addresses = repository.findByCity(wrongCity);

		// then
		assertThat(addresses)
				.isNotNull()
				.isEmpty();
	}

	/*
	Save methods
	 */
	@Test
	public void whenAddressSaved_thenReturnAddress() {
		// when
		Address newAddress = new Address(secondParisAddressLabel, parisAddressZipcode, parisAddressCity, parisAddressCountry);
		Address found = repository.saveAndFlush(newAddress);

		// then
		assertThat(found).isNotNull();
		assertThat(found.getLabel()).isEqualTo(secondParisAddressLabel);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void whenAddressSaved_NoLabel_thenDataIntegrityViolationException() {
		// when
		Address emptyAddress = new Address();
		repository.saveAndFlush(emptyAddress);

		// then throw DataIntegrityViolationException
	}

	@Test(expected = ConstraintViolationException.class)
	public void whenAddressSaved_LabelUnderSizeMin_thenConstraintViolationException() {
		// when
		Address address = new Address("A", parisAddressZipcode, parisAddressCity, parisAddressCountry);
		repository.saveAndFlush(address);

		// then throw ConstraintViolationException
	}

	@Test(expected = ConstraintViolationException.class)
	public void whenAddressSaved_ZipcodeWrongSize_thenConstraintViolationException() {
		// when
		Address address = new Address(parisAddressLabel, "123", parisAddressCity, parisAddressCountry);
		repository.saveAndFlush(address);

		// then throw ConstraintViolationException
	}

	/*
	Update methods
	 */
	@Test
	public void whenAddressUpdated_thenReturnAddress() {
		// when
		Address addressToUpdate = repository.getOne(parisAddress.getAddressId());
		addressToUpdate.setLabel(secondParisAddressLabel);
		Address updatedAddress = repository.saveAndFlush(addressToUpdate);

		// then
		assertThat(updatedAddress).isNotNull();
		assertThat(updatedAddress.getLabel()).isEqualTo(secondParisAddressLabel);
	}

	@Test(expected = ConstraintViolationException.class)
	public void whenAddressUpdated_thenReturnNull() {
		// when
		Address addressToUpdate = repository.getOne(parisAddress.getAddressId());
		addressToUpdate.setLabel("A");
		Address updatedAddress = repository.saveAndFlush(addressToUpdate);

		// then throw ConstraintViolationException
	}

}
