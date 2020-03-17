package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordAlreadyExistException;
import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordIdMismatchException;
import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.AnnouncementRepository;
import com.leopaulmartin.spring.leboncoinecole.services.AnnouncementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//Use @Transactional to cancel the transaction in case of any exception
//https://netsurfingzone.com/spring/spring-transaction-management-example-using-spring-boot/
//https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#transactions
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {
	private static final Logger logger = LoggerFactory.getLogger(AnnouncementServiceImpl.class);

	@Autowired
	private AnnouncementRepository repository;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, timeout = 10)
	/*
	About @Transactional options
	https://netsurfingzone.com/spring/spring-transaction-management-basic/
	 */
	@Override
	public List<Announcement> getAllAnnouncements() {
		return repository.findAll();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public Announcement getAnnouncementById(Long id) throws RecordNotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("", id));
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<Announcement> getAnnouncementForCategory(Long categoryId) throws RecordNotFoundException {
//		if (repository.findByLabel(label) == null)
//			throw new RecordNotFoundException("label", label);
//		return repository.findByLabel(label);
		return null;
	}

	/*
	Difference between repository.getOne and findById
	 getOne: Lazy, no properties access required, EntityNotFoundException, better performance
	 findById: Eager, real object mapping to a row in the database, Null, database cost
	https://www.javacodemonk.com/difference-between-getone-and-findbyid-in-spring-data-jpa-3a96c3ff
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Announcement createAnnouncement(Announcement announcement) throws RecordAlreadyExistException {
//		if (repository.findByLabel(category.getLabel()) != null)
//			throw new RecordAlreadyExistException("label", category.getLabel());

//		return repository.saveAndFlush(category);
		return null;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Announcement updateAnnouncement(Long id, Announcement announcement) throws RecordIdMismatchException, RecordAlreadyExistException, RecordNotFoundException {
//		if (!category.getCategoryId().equals(id)) {
//			throw new RecordIdMismatchException();
//		}
//		if (repository.findByLabel(category.getLabel()) != null)
//			throw new RecordAlreadyExistException("label", category.getLabel());
//
//		Category existingCategory = repository.findById(id)
//				.orElseThrow(() -> new RecordNotFoundException("id", id));
//		//copy : source, target, ignoreProperties
//		BeanUtils.copyProperties(category, existingCategory, "category_id");
//		return repository.saveAndFlush(existingCategory);
		return null;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteAnnouncementById(Long id) throws RecordNotFoundException {
		repository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("id", id));
		repository.deleteById(id);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteAllAnnouncements() {
		return;
	}
}
