package com.leopaulmartin.spring.leboncoinecole.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity(name = "schools")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "link", length = 200)
    private String link;

    @OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @JoinTable(
            name = "school_address",
            joinColumns = @JoinColumn(name = "school_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Address address;

    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinTable(
            name = "school_students",
            joinColumns = @JoinColumn(name = "school_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;

    public School() {
    }

    public Long getSchoolId() { return schoolId; }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Student> getStudents() { return students; }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "School{" +
                "schoolId=" + schoolId +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", address=" + address +
                ", students=" + students +
                '}';
    }
}
