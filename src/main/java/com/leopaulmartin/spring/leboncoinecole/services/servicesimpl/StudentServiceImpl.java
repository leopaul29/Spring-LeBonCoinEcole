package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.User;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.StudentRepository;
import com.leopaulmartin.spring.leboncoinecole.services.StudentService;
import com.leopaulmartin.spring.leboncoinecole.web.dto.AccountDto;
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

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Student createOrUpdateStudent(Student student) {
		if (student.getStudentId() == null) {
			return repository.save(student);
		} else {
			Optional<Student> found = repository.findById(student.getStudentId());

			if (found.isPresent()) {
				Student updateStudent = found.get();
				updateStudent.setPhoneNumber(student.getPhoneNumber());
				updateStudent.setPhoto(student.getPhoto());
				updateStudent.setSchool(student.getSchool());
				updateStudent.setAnnouncements(student.getAnnouncements());

				return repository.save(updateStudent);
			} else {
				return null;
			}
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Student updateAccount(AccountDto accountDto) {
		Optional<Student> found = repository.findById(accountDto.getStudentId());
		if (found.isPresent()) {
			Student updateStudent = found.get();
			updateStudent.setPhoneNumber(accountDto.getPhoneNumber());
			updateStudent.setPhoto(accountDto.getPhoto());
			updateStudent.setSchool(accountDto.getSchool());
			return repository.save(updateStudent);
		} else {
			return null;
		}
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
}
