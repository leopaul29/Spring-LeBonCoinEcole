package com.leopaulmartin.spring.leboncoinecole.services.servicesimpl;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.persistence.repositories.AnnouncementRepository;
import com.leopaulmartin.spring.leboncoinecole.services.AnnouncementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;

@RunWith(SpringRunner.class)
public class AnnouncementServiceImplIntegrationTest {
	private static final Logger logger = LoggerFactory.getLogger(AnnouncementServiceImplIntegrationTest.class);

	public Announcement announcement;
	public Long announcementId = 1L;
	public String announcementTitle = "Super annonce";
	public String announcementDescription = "La description de ma super annonce";
	public float announcementPrice = 10.0f;

	@Autowired
	private AnnouncementService service;

	@MockBean
	private AnnouncementRepository repository;

	@Before
	public void setUp() {
		// create tested category object
		announcement = new Announcement(announcementTitle, announcementDescription, announcementPrice);
		announcement.setAnnouncementId(announcementId);

		// mock getOne
		Mockito.when(repository.getOne(announcementId))
				.thenReturn(announcement);
		// mock findById
		Mockito.when(repository.findById(announcementId))
				.thenReturn(java.util.Optional.ofNullable(announcement));
		// mock save
		Mockito.when(repository.saveAndFlush(announcement))
				.thenReturn(announcement);
	}

	// write test cases here

	/*
	Find methods
	 */
	@Test
	public void whenGetAnnouncementById_thenReturnAnnouncement() {
		// when
		Announcement found = service.getAnnouncementById(announcementId);

		// then
		contactAnnouncementTest(found);
	}

	@Test
	public void whenGetAnnouncementById_thenReturnNull() {
		// when
		Long AnnouncementId = 2L;
		Announcement found = service.getAnnouncementById(AnnouncementId);

		// then
		assertThat(found).isNull();
	}

	/*
	Save methods
	 */
	@Test
	public void whenCreateAnnouncement_thenReturnAnnouncement() {
		// reset
		reset(repository);
		// mock save
		Mockito.when(repository.saveAndFlush(announcement))
				.thenReturn(announcement);

		// when
		Announcement found = service.createAnnouncement(announcement);

		// then
		contactAnnouncementTest(found);
	}

	@Test
	public void whenCreateAnnouncementFull_thenReturnAnnouncement() {
		// reset
		reset(repository);
		// mock save
		Mockito.when(repository.saveAndFlush(announcement))
				.thenReturn(announcement);

		// when
		Announcement found = service.createAnnouncement(announcement);

		// then
		assertThat(found).isNotNull();
		assertThat(found.getAnnouncementId())
				.isEqualTo(announcementId);
		assertThat(found.getTitle())
				.isEqualTo(announcementTitle);
		assertThat(found.getDescription())
				.isEqualTo(announcementDescription);
	}

	/*
	Update methods
	 */
	@Test
	public void whenUpdateAnnouncement_thenReturnAnnouncement() {
		// when
		String newTitle = "new-user";
		String newDescription = "new-pass";
		float newPrice = 12.0f;
		announcement.setTitle(newTitle);
		announcement.setDescription(newDescription);
		announcement.setPrice(newPrice);
		Announcement found = service.updateAnnouncement(announcementId, announcement);

		// then
		assertThat(found)
				.isNotNull();
		assertThat(found.getAnnouncementId())
				.isEqualTo(announcementId);
		assertThat(found.getTitle())
				.isEqualTo(newTitle);
		assertThat(found.getDescription())
				.isEqualTo(newDescription);
		assertThat(found.getPrice())
				.isEqualTo(newPrice);
	}

	@Test
	public void whenUpdateCategory_thenWrongId() {
		// when
		Long wrongId = 2L;
		Announcement found = service.updateAnnouncement(wrongId, announcement);

		// then
		assertThat(found).isNull();
	}

	@Test
	public void whenUpdateCategory_thenLabelCannotBeNull() {
		// when
		announcement.setTitle(null);
		announcement.setDescription(null);
		Announcement found = service.updateAnnouncement(announcementId, announcement);

		// then
		assertThat(found).isNull();
	}

	@Test
	public void whenUpdateCategory_thenNullIdNotExist() {
		// when
		Announcement secondAnnouncement = new Announcement();
		secondAnnouncement.setTitle("announcementBis");
		secondAnnouncement.setDescription("announcementBis");
		Long secondAnnouncementId = 18L;
		secondAnnouncement.setAnnouncementId(secondAnnouncementId);
		Announcement found = service.updateAnnouncement(secondAnnouncementId, secondAnnouncement);

		// then
		assertThat(found).isNull();
	}


	/*
	Delete methods
	 */
	@Test
	public void whenDeleteAnnouncement_thenNothingHappen() {
		// when
		Long wrongId = 14L;
		service.deleteAnnouncementById(wrongId);
	}

	/*
	IsValid
	 */
	@Test
	public void isAnnouncementValid_thenReturnTrue() {
		// when
		boolean AnnouncementValid = service.isAnnouncementValid(announcement);

		// then
		assertThat(AnnouncementValid).isTrue();
	}

	@Test
	public void isAnnouncementValid_thenReturnFalse() {
		// when
		boolean AnnouncementValid = service.isAnnouncementValid(new Announcement());

		// then
		assertThat(AnnouncementValid).isFalse();
	}

	private void contactAnnouncementTest(Announcement announcementTested) {
		assertThat(announcementTested).isNotNull();
		assertThat(announcementTested.getAnnouncementId()).isEqualTo(announcementId);
		assertThat(announcementTested.getTitle()).isEqualTo(announcementTitle);
		assertThat(announcementTested.getDescription()).isEqualTo(announcementDescription);
		assertThat(announcementTested.getPrice()).isEqualTo(announcementPrice);
	}

	/*
	To check the Service class, we need to have an instance of Service class created and available as a @Bean so that we can @Autowire it in our test class. This configuration is achieved by using the @TestConfiguration annotation.
	 */
	@TestConfiguration
	static class AnnouncementServiceImplTestContextConfiguration {

		@Bean
		public AnnouncementService announcementService() {
			return new AnnouncementServiceImpl();
		}
	}
}
