package com.leopaulmartin.spring.leboncoinecole.models;

import javax.persistence.Column;

public abstract class User {
    @Column(name = "username", length = 30)
    public String username;

    @Column(name = "password", length = 30)
    public String password;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
