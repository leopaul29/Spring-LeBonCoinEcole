package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Address;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.AddressRepository;
import com.leopaulmartin.spring.leboncoinecole.services.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
	private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

	@Autowired
	private AddressRepository repository;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public Address getAddressById(Long id) {
		return repository.getOne(id);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Address createAddress(Address address) {
		if (!isAddressValid(address)) {
			return null;
		}

		return repository.saveAndFlush(address);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Address updateAddress(Long id, Address address) {
		if (!address.getAddressId().equals(id)) {
			logger.error("mistmatch id");
			return null;
		}
		if (!isAddressValid(address)) {
			return null;
		}

		Optional<Address> existingAddress = repository.findById(id);
		if (existingAddress.isPresent()) {
			Address foundAddress = existingAddress.get();
			foundAddress.setLabel(address.getLabel());
			return repository.saveAndFlush(foundAddress);
		} else {
			logger.error("not found");
			return null;
		}
	}

	// should not be used
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteAddressById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public boolean isAddressValid(Address address) {
		Boolean[] isValidTab = new Boolean[4];
		isValidTab[0] = address.getLabel() != null;
		isValidTab[1] = address.getZipCode() != null;
		isValidTab[2] = address.getCity() != null;
		isValidTab[3] = address.getCountry() != null;

		for (int i = 0; i < isValidTab.length; i++) {
			boolean isValid = isValidTab[i];
			if (!isValid) {
				logger.error("validation failed");
				return false;
			}
		}

		logger.error("validation success");
		return true;
	}
}
