package com.leopaulmartin.spring.leboncoinecole.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity(name = "phonenumbers")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phonenumber_id")
    private int phonenumberId;

    @Column(name = "number", length = 10)
    private String number;

    public PhoneNumber() {
    }

    public PhoneNumber(String number) {
        this.number = number;
    }

    public int getPhonenumberId() {
        return phonenumberId;
    }

    public void setPhonenumberId(int phonenumberId) {
        this.phonenumberId = phonenumberId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "phonenumberId=" + phonenumberId +
                ", number='" + number + '\'' +
                '}';
    }
}
