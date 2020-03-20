package com.leopaulmartin.spring.leboncoinecole.services;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Address;
import org.springframework.stereotype.Component;

@Component
public interface AddressService {
	Address getAddressById(Long id);

	Address createAddress(Address address);

	Address updateAddress(Long id, Address address);

	void deleteAddressById(Long id);
}
