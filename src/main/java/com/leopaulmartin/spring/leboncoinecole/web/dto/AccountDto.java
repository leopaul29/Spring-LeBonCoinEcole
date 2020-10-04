package com.leopaulmartin.spring.leboncoinecole.web.dto;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AccountDto {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String email;

    private String created;

    private Long studentId;

    private byte[] photo;
    @NotEmpty
    private String phoneNumber;
    @NotNull
    private School school;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", created='" + created + '\'' +
                ", studentId=" + studentId +
                ", photo=" + (photo.length>0) +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", school=" + school +
                '}';
    }
}
