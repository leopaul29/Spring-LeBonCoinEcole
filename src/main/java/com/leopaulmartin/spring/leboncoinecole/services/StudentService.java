package com.leopaulmartin.spring.leboncoinecole.services;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.User;
import com.leopaulmartin.spring.leboncoinecole.web.dto.AccountDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentService {
	List<Student> getAllStudents();

	Student getStudentById(Long id) throws RecordNotFoundException;

//	Student getStudentByUserId(Long id) throws RecordNotFoundException;
//
//	Student getStudentByUserProfile(User userProfile)throws RecordNotFoundException;

	Student createOrUpdateStudent(Student student);

	Student updateAccount(AccountDto accountDto);

	void deleteStudentById(Long id) throws RecordNotFoundException;

//	void deleteStudentByUserId(Long id) throws RecordNotFoundException;

//	boolean isStudentValid(Student student);
}
