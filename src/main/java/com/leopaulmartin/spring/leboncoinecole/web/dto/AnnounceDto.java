package com.leopaulmartin.spring.leboncoinecole.web.dto;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AnnounceDto {

    public Long announcementId;

    @NotEmpty
    public String title;

    @NotNull(message = "You must select a category")
    public Category category;

    @Min(0)
    public float price;

    @NotEmpty
    public String description;

    public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AnnounceDto{" +
                "title='" + title + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
