package com.dh.DentalClinicMVC.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Dentists")
public class Dentist {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name ="dentist_id")
    private Long id;

    @Column(name="registration")
    private Integer registration;

    @Column(name ="name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    public Dentist() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRegistration() {
        return registration;
    }

    public void setRegistration(Integer registration) {
        this.registration = registration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
