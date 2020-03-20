package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Address;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.AddressRepository;
import com.leopaulmartin.spring.leboncoinecole.services.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
		if (!addressIsValid(address)) {
			logger.error("validation failed");
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
		if (!addressIsValid(address)) {
			logger.error("validation failed");
			return null;
		}
//		Address existingAddress = repository.findById(id)
//				.orElseThrow(() -> new RecordNotFoundException("id", id));
//		//copy : source, target, ignoreProperties
//		BeanUtils.copyProperties(address, existingAddress, "category_id");
//		return repository.saveAndFlush(existingAddress);

		Optional<Address> existingAddress = repository.findById(id);
		if (existingAddress.isPresent()) {
			Address foundAddress = existingAddress.get();
			foundAddress.setLabel(address.getLabel());
			//copy : source, target, ignoreProperties
			BeanUtils.copyProperties(foundAddress, existingAddress, "category_id");
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

	private boolean addressIsValid(Address address) {
		boolean isValid = address.getLabel() != null;
		logger.error("isValid:" + isValid);
		return isValid;
	}
}
