package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import com.leopaulmartin.spring.leboncoinecole.utils.Utils;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity(name = "announcements")
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Announcement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "announcement_id")
	private Long announcementId;

	@Column(name = "is_announcement")
	private boolean isAnnouncement;

	@Column(name = "title", length = 200, nullable = false)
	@NotNull(message = "Cannot be null")
	private String title;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "description", length = 2000, nullable = false)
	@NotNull(message = "Cannot be null")
	private String description;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name = "photo")
	private byte[] photo;

	@Column(name = "price", length = 10)
	@Min(value = 0, message = "Cannot be negatif")
	private float price;

	private Instant created;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "is_closed")
	private boolean isClosed;

	@OneToOne
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	private Student student;

	/*
	Constructors
	 */
	public Announcement() {
		// sale by default
		this.isAnnouncement = true;
		// server date when announcement created
		this.created = Instant.now();
		// life of the announcement set to 3 month maximum
		GregorianCalendar gcalendar = new GregorianCalendar();
		gcalendar.add(3, GregorianCalendar.MONTH);
		this.endDate = gcalendar.getGregorianChange();
		// isClosed by default
		isClosed = false;
	}

	public Announcement(String title, String description, Category category, float price) {
		this();

		this.title = title;
		this.description = description;
		this.category = category;
		this.price = price;
	}

	/*
	 Methods
	 */
	public void makeItaSearch() {
		this.setAnnouncement(false);
	}

	/*
	 Getters & Setters
	 */
	public Long getAnnouncementId() {
		return announcementId;
	}

	public void setAnnouncementId(Long announcementId) {
		this.announcementId = announcementId;
	}

	public boolean isAnnouncement() {
		return isAnnouncement;
	}

	public void setAnnouncement(boolean announcement) {
		isAnnouncement = announcement;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

//	public Date getCreationDate() {
//		return creationDate;
//	}
//
//	public void setCreationDate(Date creationDate) {
//		this.creationDate = creationDate;
//	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean closed) {
		isClosed = closed;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getCreated() {
		return Utils.getFormattedDate(created);
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Announcement{" +
				"announcementId=" + announcementId +
				", isAnnouncement=" + isAnnouncement +
				", title='" + title + '\'' +
				", photo=" + (photo != null) +
				", price=" + price +
				", created=" + created +
//				", creationDate=" + creationDate +
				", endDate=" + endDate +
				", description='" + description + '\'' +
				", isClosed=" + isClosed +
				", category=" + category +
//				", student=" + student +
				'}';
	}
}
