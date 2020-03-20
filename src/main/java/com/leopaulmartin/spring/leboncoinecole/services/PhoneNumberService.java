package com.leopaulmartin.spring.leboncoinecole.services;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.PhoneNumber;
import org.springframework.stereotype.Component;

@Component
public interface PhoneNumberService {
	PhoneNumber getPhoneNumberById(Long id);

	PhoneNumber createPhoneNumber(PhoneNumber phoneNumber);

	PhoneNumber updatePhoneNumber(Long id, PhoneNumber phoneNumber);

	//TODO: Should be remove if not used
	void deletePhoneNumberById(Long id);

	boolean isPhoneNumberValid(PhoneNumber phoneNumber);
}
