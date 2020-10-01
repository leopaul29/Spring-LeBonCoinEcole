package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.AnnouncementRepository;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.CategoryRepository;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.SchoolRepository;
import com.leopaulmartin.spring.leboncoinecole.services.AnnouncementService;
import com.leopaulmartin.spring.leboncoinecole.web.dto.AnnounceDto;
import com.leopaulmartin.spring.leboncoinecole.web.dto.SearchForm;
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
public class AnnouncementServiceImpl implements AnnouncementService {
	private static final Logger logger = LoggerFactory.getLogger(AnnouncementServiceImpl.class);

	@Autowired
	private AnnouncementRepository repository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private SchoolRepository schoolRepository;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, timeout = 10)
	@Override
	public List<Announcement> getAllAnnouncements() {
		return repository.findAll();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public Announcement getAnnouncementById(Long id) {
		return repository.getOne(id);
	}

//	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//	@Override
//	public List<Announcement> getAnnouncementsFromSearchForm(SearchForm searchForm) {
//		String type = null;
//		if (type.equals("announce")) {
//			type = "true";
//		}
//		if (type.equals("search")){
//			type = "false";
//		}
//		List<Announcement> announcementsFromSearchForm = repository.getAnnouncementsFromSearchForm(
//				type,
//				searchForm.getCategoryId(),
//				searchForm.getKeywordsInput()
//		);
//		logger.debug("announcementsFromSearchForm: "+announcementsFromSearchForm);
//
//		return announcementsFromSearchForm;
//	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<Announcement> getAnnouncementsByType(String type) {
		if (type.equals("announce")) {
			return repository.findByIsAnnouncement(true);
		}
		if (type.equals("search")){
			return repository.findByIsAnnouncement(false);
		}

		return repository.findAll();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<Announcement> getAnnouncementsByCategory(Long categoryId) {
		Category category = categoryRepository.getOne(categoryId);
		return repository.findByCategory(category);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<Announcement> getAnnouncementsByKeywordInput(String keywordInput) {
		return repository.findByTitle(keywordInput);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Announcement createAnnouncement(Announcement announcement) {
		if (!isAnnouncementValid(announcement)) {
			return null;
		}

		return repository.saveAndFlush(announcement);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Announcement updateAnnouncement(Long id, Announcement announcement) {
		if (!announcement.getAnnouncementId().equals(id)) {
			logger.error("mistmatch id");
			return null;
		}
		if (!isAnnouncementValid(announcement)) {
			return null;
		}

		Optional<Announcement> existingAnnouncement = repository.findById(id);
		if (existingAnnouncement.isPresent()) {
			Announcement foundAnnouncement = existingAnnouncement.get();
			foundAnnouncement.setTitle(announcement.getTitle());
			foundAnnouncement.setDescription(announcement.getDescription());
			foundAnnouncement.setPrice(announcement.getPrice());
			return repository.saveAndFlush(foundAnnouncement);
		} else {
			logger.error("not found");
			return null;
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Announcement createOrUpdateAnnouncement(Announcement announcement) {
		if (announcement.getAnnouncementId() == null) {
			return repository.save(announcement);
		} else {
			Optional<Announcement> found = repository.findById(announcement.getAnnouncementId());

			if (found.isPresent()) {
				Announcement newAnnouncement = found.get();
				newAnnouncement.setTitle(announcement.getTitle());
				newAnnouncement.setDescription(announcement.getDescription());
				newAnnouncement.setPrice(announcement.getPrice());
				newAnnouncement.setAnnouncement(announcement.isAnnouncement());
				newAnnouncement.setCategory(announcement.getCategory());
				newAnnouncement.setEndDate(announcement.getEndDate());
				newAnnouncement.setPhoto(announcement.getPhoto());

				return repository.save(newAnnouncement);
			} else {
				return repository.save(announcement);
			}
		}
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Announcement createOrUpdateAnnouncement(AnnounceDto announceDto) {
		Announcement newAnnouncement = new Announcement();
		newAnnouncement.setTitle(announceDto.getTitle());
		newAnnouncement.setDescription(announceDto.getDescription());
		newAnnouncement.setPrice(announceDto.getPrice());
		newAnnouncement.setCategory(announceDto.getCategory());

		if (announceDto.getAnnouncementId() == null) {
			return repository.save(newAnnouncement);
		} else {
			Optional<Announcement> found = repository.findById(announceDto.getAnnouncementId());

			if (found.isPresent()) {
				Announcement updateAnnouncement = found.get();
				updateAnnouncement.setTitle(announceDto.getTitle());
				updateAnnouncement.setDescription(announceDto.getDescription());
				updateAnnouncement.setPrice(announceDto.getPrice());
//				updateAnnouncement.setAnnouncement(announcement.isAnnouncement());
				updateAnnouncement.setCategory(announceDto.getCategory());
//				updateAnnouncement.setEndDate(announcement.getEndDate());
//				updateAnnouncement.setPhoto(announcement.getPhoto());
				return repository.save(updateAnnouncement);
			} else {
				return repository.save(newAnnouncement);
			}
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteAnnouncementById(Long id) throws RecordNotFoundException {
		Optional<Announcement> announcement = repository.findById(id);

		if (announcement.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No announcement record exist for given id");
		}
	}

	@Override
	public boolean isAnnouncementValid(Announcement announcement) {
		Boolean[] isValidTab = new Boolean[4];
		isValidTab[0] = announcement.getTitle() != null;
		isValidTab[1] = announcement.getDescription() != null;
		isValidTab[2] = announcement.getCategory() != null;
		isValidTab[3] = announcement.getPrice() >= 0;

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
