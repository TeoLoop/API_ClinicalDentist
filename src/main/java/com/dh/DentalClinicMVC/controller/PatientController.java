package com.dh.DentalClinicMVC.controller;

import com.dh.DentalClinicMVC.entity.Patient;
import com.dh.DentalClinicMVC.exception.ResourceNotFoundException;
import com.dh.DentalClinicMVC.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PatientController {

    private IPatientService patientService;

    @Autowired
    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    //un endpoint que nos permita agregar un paciente
    @PostMapping
    public Patient save(@RequestBody Patient patient){
        return patientService.save(patient);
    }

    //agregar un endpoint que permita actualizar un paciente que ya este registrado
    @PutMapping
    public void update(@RequestBody Patient patient){
        patientService.update(patient);
    }

    @GetMapping
    public List<Patient> findAll(){
        return patientService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        patientService.delete(id);
        return ResponseEntity.ok("Se eliminot el paciente con id: " + id);
    }
}
