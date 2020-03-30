package com.leopaulmartin.spring.leboncoinecole.services;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentService {
	List<Student> getAllStudents();

	Student getStudentById(Long id) throws RecordNotFoundException;

	Student getStudentByUserId(Long id) throws RecordNotFoundException;

	Student createOrUpdateStudent(Student student);

	void deleteStudentById(Long id) throws RecordNotFoundException;

	void deleteStudentByUserId(Long id) throws RecordNotFoundException;

	boolean isStudentValid(Student student);
}
