package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.User;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.StudentRepository;
import com.leopaulmartin.spring.leboncoinecole.services.StudentService;
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
public class StudentServiceImpl implements StudentService {
	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Autowired
	private StudentRepository repository;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<Student> getAllStudents() {
		return repository.findAll();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public Student getStudentById(Long id) throws RecordNotFoundException {
		Optional<Student> student = repository.findById(id);

		if (student.isPresent()) {
			return student.get();
		} else {
			throw new RecordNotFoundException("No student record exist for given id");
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public Student getStudentByUserId(Long id) throws RecordNotFoundException {
		return repository.findByUserId(id);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public Student getStudentByUserProfile(User userProfile) throws RecordNotFoundException {
		return repository.findByUserProfile(userProfile);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Student createOrUpdateStudent(Student student) {
//		if (student.getUserId() == null) {
//			return repository.save(student);
//		} else {
//			Optional<Student> found = repository.findById(student.getUserId());
//
//			if (found.isPresent()) {
//				Student newStudent = found.get();
//				newStudent.setUsername(student.getUsername());
//				newStudent.setPassword(student.getPassword());
//				newStudent.setFirstName(student.getFirstName());
//				newStudent.setLastName(student.getLastName());
//				newStudent.setEmail(student.getEmail());
//				newStudent.setPhoneNumber(student.getPhoneNumber());
//				newStudent.setPhoto(student.getPhoto());
//				newStudent.setSchool(student.getSchool());
//
//				return repository.save(newStudent);
//			} else {
//				return repository.save(student);
//			}
//		}
		return repository.save(student);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteStudentById(Long id) throws RecordNotFoundException {
		Optional<Student> student = repository.findById(id);

		if (student.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No student record exist for given id");
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteStudentByUserId(Long id) throws RecordNotFoundException {
		Student student = getStudentByUserId(id);

		if (student != null) {
			repository.delete(student);
		}
	}

	@Override
	public boolean isStudentValid(Student student) {
		Boolean[] isValidTab = new Boolean[2];
//		isValidTab[0] = student.getUsername() != null;
//		isValidTab[1] = student.getPassword() != null;

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
