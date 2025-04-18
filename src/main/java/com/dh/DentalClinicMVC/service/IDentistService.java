package com.dh.DentalClinicMVC.service;

import com.dh.DentalClinicMVC.model.Dentist;

import java.util.List;
import java.util.Optional;

public interface IDentistService {

    Dentist save(Dentist dentist);

    Optional<Dentist> findById(Long id);

    void update(Dentist dentist);

    void  delete(Long id);

    List<Dentist> findAll();
}
