package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.StudentRepository;
import com.leopaulmartin.spring.leboncoinecole.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Autowired
	private StudentRepository repository;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public Student getStudentById(Long id) {
		return repository.getOne(id);
	}

//	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//	@Override
//	public int getAnnouncementCount(Student student) {
//		return student.getAnnouncements().size();
//	}


	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Student createStudent(Student student) {
		if (!isStudentValid(student)) {
			return null;
		}

		return repository.saveAndFlush(student);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Student updateStudent(Long id, Student student) {
		if (!student.getStudentId().equals(id)) {
			logger.error("mistmatch id");
			return null;
		}
		if (!isStudentValid(student)) {
			return null;
		}

		Optional<Student> existingStudent = repository.findById(id);
		if (existingStudent.isPresent()) {
			Student foundStudent = existingStudent.get();
			foundStudent.setUsername(student.getUsername());
			foundStudent.setPassword(student.getPassword());
			return repository.saveAndFlush(foundStudent);
		} else {
			logger.error("not found");
			return null;
		}
	}

	// should not be used
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteStudentById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public boolean isStudentValid(Student student) {
		Boolean[] isValidTab = new Boolean[2];
		isValidTab[0] = student.getUsername() != null;
		isValidTab[1] = student.getPassword() != null;

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
