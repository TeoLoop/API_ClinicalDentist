package com.dh.DentalClinicMVC.service.impl;

import com.dh.DentalClinicMVC.entity.Patient;
import com.dh.DentalClinicMVC.exception.ResourceNotFoundException;
import com.dh.DentalClinicMVC.repository.PatientRepository;
import com.dh.DentalClinicMVC.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService implements IPatientService {

    private PatientRepository patientRepository;
    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public void update(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Patient> patient = findById(id);

        if (patient.isPresent()){
            patientRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("No se pudo eliminar el Paciente " + id + ", no se econtro en la BD");
        }


    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }
}
