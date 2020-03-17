package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class AddressRepositoryIntegrationTest {

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private AddressRepository repository;

	// write test cases here
	@Test
	public void whenFindById_thenReturnAddress() {
		// given
		Address parisAddress = new Address("Rue du Faubourg Saint-Honoré", "75123", "Paris", "France");
		parisAddress.setLongitude(48.869931f);
		parisAddress.setLatitude(2.318397f);
		entityManager.persist(parisAddress);
		entityManager.flush();

		// when
		Optional<Address> existing = repository.findById(parisAddress.getAddressId());

		// then
		assertThat(existing.get()).isNotNull();
		Address found = existing.get();
		assertThat(found.getLabel()).isEqualTo(parisAddress.getLabel());
		assertThat(found.getZipCode()).isEqualTo(parisAddress.getZipCode());
		assertThat(found.getCity()).isEqualTo(parisAddress.getCity());
		assertThat(found.getCountry()).isEqualTo(parisAddress.getCountry());
		assertThat(found.getLongitude()).isEqualTo(parisAddress.getLongitude());
		assertThat(found.getLatitude()).isEqualTo(parisAddress.getLatitude());
	}

	@Test
	public void whenFindByCity_thenReturnAddress() {
		// given
		Address parisAddress = new Address("Rue du Faubourg Saint-Honoré", "75123", "Paris", "France");
		parisAddress.setLongitude(48.869931f);
		parisAddress.setLatitude(2.318397f);
		entityManager.persist(parisAddress);
		entityManager.flush();

		// when
		Address found = repository.findByCity(parisAddress.getCity());

		// then
		assertThat(found.getLabel()).isEqualTo(parisAddress.getLabel());
		assertThat(found.getZipCode()).isEqualTo(parisAddress.getZipCode());
		assertThat(found.getCity()).isEqualTo(parisAddress.getCity());
		assertThat(found.getCountry()).isEqualTo(parisAddress.getCountry());
		assertThat(found.getLongitude()).isEqualTo(parisAddress.getLongitude());
		assertThat(found.getLatitude()).isEqualTo(parisAddress.getLatitude());
	}
}
