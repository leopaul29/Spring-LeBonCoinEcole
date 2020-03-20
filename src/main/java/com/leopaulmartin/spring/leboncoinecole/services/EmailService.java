package com.leopaulmartin.spring.leboncoinecole.services;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Email;
import org.springframework.stereotype.Component;

@Component
public interface EmailService {
	Email getEmailById(Long id);

	Email createEmail(Email email);

	Email updateEmail(Long id, Email email);

	void deleteEmailById(Long id);

	boolean isEmailValid(Email email);
}
