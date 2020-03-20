package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.PhoneNumber;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.PhoneNumberRepository;
import com.leopaulmartin.spring.leboncoinecole.services.PhoneNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PhoneNumberServiceImpl implements PhoneNumberService {
	private static final Logger logger = LoggerFactory.getLogger(PhoneNumberServiceImpl.class);

	@Autowired
	private PhoneNumberRepository repository;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public PhoneNumber getPhoneNumberById(Long id) {
		return repository.getOne(id);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PhoneNumber createPhoneNumber(PhoneNumber phoneNumber) {
		if (!isPhoneNumberValid(phoneNumber)) {
			return null;
		}

		return repository.saveAndFlush(phoneNumber);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PhoneNumber updatePhoneNumber(Long id, PhoneNumber phoneNumber) {
		if (!phoneNumber.getPhonenumberId().equals(id)) {
			logger.error("mistmatch id");
			return null;
		}
		if (!isPhoneNumberValid(phoneNumber)) {
			return null;
		}

		Optional<PhoneNumber> existingPhoneNumber = repository.findById(id);
		if (existingPhoneNumber.isPresent()) {
			PhoneNumber foundPhoneNumber = existingPhoneNumber.get();
			foundPhoneNumber.setNumber(phoneNumber.getNumber());
			return repository.saveAndFlush(foundPhoneNumber);
		} else {
			logger.error("not found");
			return null;
		}
	}

	// should not be used
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deletePhoneNumberById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public boolean isPhoneNumberValid(PhoneNumber phoneNumber) {
		boolean isValid = phoneNumber.getNumber() != null;

		if (isValid) logger.error("validation success");
		else logger.error("validation failed");
		return isValid;
	}
}
