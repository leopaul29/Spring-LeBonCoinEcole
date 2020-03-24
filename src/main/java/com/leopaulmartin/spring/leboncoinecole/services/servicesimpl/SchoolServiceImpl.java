package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.SchoolRepository;
import com.leopaulmartin.spring.leboncoinecole.services.AddressService;
import com.leopaulmartin.spring.leboncoinecole.services.SchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {
	private static final Logger logger = LoggerFactory.getLogger(SchoolServiceImpl.class);

	@Autowired
	private SchoolRepository repository;
	@Autowired
	private AddressService addressService;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, timeout = 10)
	@Override
	public List<School> getAllSchools() {
		return repository.findAll();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public School getSchoolById(Long id) throws RecordNotFoundException {
		Optional<School> school = repository.findById(id);

		if (school.isPresent()) {
			return school.get();
		} else {
			throw new RecordNotFoundException("No school record exist for given id");
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<Student> getStudentsForSchool(Long schoolId) {
//		return repository.findSchoolsByCategory(categoryId);
		return null;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public School createSchool(School school) {
		if (addressService.createAddress(school.getAddress()) == null) {
			return null;
		}
		if (!isSchoolValid(school)) {
			return null;
		}

		return repository.saveAndFlush(school);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public School createOrUpdateSchool(School school) {
		if (school.getSchoolId() == null) {
			return repository.save(school);
		} else {
			Optional<School> found = repository.findById(school.getSchoolId());

			if (found.isPresent()) {
				School newSchool = found.get();
				newSchool.setName(school.getName());
				newSchool.setAddress(school.getAddress());
				newSchool.setLink(school.getLink());

				return repository.save(newSchool);
			} else {
				return repository.save(school);
			}
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public School updateSchool(Long id, School school) {
		if (!school.getSchoolId().equals(id)) {
			logger.error("mistmatch id");
			return null;
		}
		if (!isSchoolValid(school)) {
			return null;
		}

		Optional<School> existingSchool = repository.findById(id);
		if (existingSchool.isPresent()) {
			School foundSchool = existingSchool.get();
			foundSchool.setName(school.getName());
			return repository.saveAndFlush(foundSchool);
		} else {
			logger.error("not found");
			return null;
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteSchoolById(Long id) throws RecordNotFoundException {
		Optional<School> school = repository.findById(id);
		logger.error("schoolserviceimpl:" + school.toString());
		if (school.isPresent()) {
			logger.error("schoolserviceimpl2:" + school.get().toString());
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No sSchool record exist for given id");
		}
	}

	@Override
	public boolean isSchoolValid(School school) {
		Boolean[] isValidTab = new Boolean[1];
		isValidTab[0] = school.getName() != null;

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
