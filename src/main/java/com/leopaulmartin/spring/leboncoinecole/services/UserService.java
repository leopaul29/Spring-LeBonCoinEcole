package com.leopaulmartin.spring.leboncoinecole.services;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.User;
import com.leopaulmartin.spring.leboncoinecole.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
	List<User> getAllUsers();

	User getUserById(Long id) throws RecordNotFoundException;

	User findByEmail(String email);

	User save(UserRegistrationDto registration);

	User createOrUpdateUser(User user);

	User createAdmin(String email, String password);

	void deleteUserById(Long id) throws RecordNotFoundException;
}