package com.leopaulmartin.spring.leboncoinecole.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity(name = "emails")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private int emailId;

    @Column(name = "email", length = 50)
    private String email;

    public Email() {
    }

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Email{" +
                "emailId=" + emailId +
                ", email='" + email + '\'' +
                '}';
    }
}
