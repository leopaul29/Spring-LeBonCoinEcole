package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Entity(name = "announcements")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private int announcementId;

    @Column(name = "is_announcement")
    private boolean isAnnouncement;

    @Column(name = "title", length = 200)
    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "price", length = 10)
    private float price;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "end_date")
    private Date endDate;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "is_closed")
    private boolean isClosed;

    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinTable(
            name = "announcement_categories",
            joinColumns = @JoinColumn(name = "announcement_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @ManyToOne
    @JoinTable(
            name = "student_announcements",
            joinColumns = @JoinColumn(name = "announcement_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Student student;

    public Announcement() {
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "announcementId=" + announcementId +
                ", isAnnouncement=" + isAnnouncement +
                ", title='" + title + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", price=" + price +
                ", creationDate=" + creationDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", isClosed=" + isClosed +
                ", categories=" + categories +
                ", student=" + student +
                '}';
    }
}
