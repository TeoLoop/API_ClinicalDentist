package com.dh.DentalClinicMVC.service;

import com.dh.DentalClinicMVC.entity.Patient;
import com.dh.DentalClinicMVC.service.impl.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//Indicar que es un test de Spring
@SpringBootTest
class PatientServiceTest {

    // si la clase es chica podemos inyectar la dependencia asi:
    @Autowired
    private PatientService patientService;
    //necesitamos inyectar porque la logica esta en nuestra clase service

    @Test
    void findById() {
        Long idPatient = 3;

        // vamos a buscar al paciente
        Patient patient = patientService.findById(idPatient);

        assertNotNull(patient);
    }
}