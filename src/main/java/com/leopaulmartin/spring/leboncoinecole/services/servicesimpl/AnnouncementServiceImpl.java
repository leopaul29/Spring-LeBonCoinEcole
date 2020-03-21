package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

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
import java.util.Optional;

@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {
	private static final Logger logger = LoggerFactory.getLogger(AnnouncementServiceImpl.class);

	@Autowired
	private AnnouncementRepository repository;

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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<Announcement> getAnnouncementForCategory(Long categoryId) {
		return repository.findAnnouncementsByCategory(categoryId);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<Announcement> getSearchesByCategory(Long categoryId) {
		return repository.findAnnouncementsByCategory(categoryId);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<Announcement> getSalesByCategory(Long categoryId) {
		return repository.findAnnouncementsByCategory(categoryId);
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
	public void deleteAnnouncementById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public boolean isAnnouncementValid(Announcement announcement) {
		Boolean[] isValidTab = new Boolean[3];
		isValidTab[0] = announcement.getTitle() != null;
		isValidTab[1] = announcement.getDescription() != null;
		isValidTab[2] = announcement.getPrice() >= 0;

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
