package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Email;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.EmailRepository;
import com.leopaulmartin.spring.leboncoinecole.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private EmailRepository repository;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public Email getEmailById(Long id) {
		return repository.getOne(id);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Email createEmail(Email email) {
		if (!isEmailValid(email)) {
			return null;
		}

		return repository.saveAndFlush(email);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Email updateEmail(Long id, Email email) {
		if (!email.getEmailId().equals(id)) {
			logger.error("mistmatch id");
			return null;
		}
		if (!isEmailValid(email)) {
			return null;
		}

		Optional<Email> existingEmail = repository.findById(id);
		if (existingEmail.isPresent()) {
			Email foundEmail = existingEmail.get();
			foundEmail.setEmail(email.getEmail());
			return repository.saveAndFlush(foundEmail);
		} else {
			logger.error("not found");
			return null;
		}
	}

	// should not be used
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteEmailById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public boolean isEmailValid(Email email) {
		boolean isValid = email.getEmail() != null;

		if (isValid) logger.error("validation success");
		else logger.error("validation failed");
		return isValid;
	}
}
