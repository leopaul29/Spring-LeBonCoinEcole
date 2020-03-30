package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Role;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.User;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.UserRepository;
import com.leopaulmartin.spring.leboncoinecole.services.StudentService;
import com.leopaulmartin.spring.leboncoinecole.services.UserService;
import com.leopaulmartin.spring.leboncoinecole.web.dto.UserRegistrationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository repository;
	@Autowired
	private StudentService studentService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<User> getAllUsers() {
		return repository.findAll();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public User getUserById(Long id) throws RecordNotFoundException {
		Optional<User> user = repository.findById(id);

		if (user.isPresent()) {
			return user.get();
		} else {
			throw new RecordNotFoundException("No user record exist for given id");
		}
	}

	// Spring security 
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

	public User save(UserRegistrationDto registration) {
		User user = new User();
//		user.setFirstName(registration.getFirstName());
//		user.setLastName(registration.getLastName());
		user.setEmail(registration.getEmail());
		user.setPassword(passwordEncoder.encode(registration.getPassword()));
		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		return repository.save(user);
	}

	public User createOrUpdateUser(User user) {
		if (user.getUserId() == null) {
			user.setRoles(Arrays.asList(new Role("ROLE_USER")));
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return repository.save(user);
		} else {
			Optional<User> found = repository.findById(user.getUserId());

			if (found.isPresent()) {
				User newUser = found.get();
//				newUser.setFirstName(user.getFirstName());
//				newUser.setLastName(user.getLastName());
				newUser.setEmail(user.getEmail());
				newUser.setPassword(passwordEncoder.encode(user.getPassword()));
				newUser.setRoles(Arrays.asList(new Role("ROLE_USER")));

				return repository.save(newUser);
			} else {
				return repository.save(user);
			}
		}
	}

	public User createAdmin(String email, String password) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));
		return repository.save(user);
	}

	public User addRole(User user, Role role) {
		if (user.getUserId() == null) {
			return null;
		}
		Optional<User> found = repository.findById(user.getUserId());

		if (found.isPresent()) {
			User newUser = found.get();
			Collection<Role> roles = user.getRoles();
			roles.add(role);
			newUser.setRoles(roles);
			return repository.save(newUser);
		}
		return null;
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteUserById(Long id) throws RecordNotFoundException {
		Optional<User> user = repository.findById(id);

		if (user.isPresent()) {
			studentService.deleteStudentByUserId(id);
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No user record exist for given id");
		}
	}
}