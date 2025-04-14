package com.dh.DentalClinicMVC.controller;

import com.dh.DentalClinicMVC.model.Appointment;
import com.dh.DentalClinicMVC.service.AppointmentService;
import com.dh.DentalClinicMVC.service.DentistService;
import com.dh.DentalClinicMVC.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class AppointmentController {

    private AppointmentService appointmentService;
    private DentistService dentistService;
    private PatientService patientService;

    public AppointmentController(AppointmentService appointmentService, DentistService dentistService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.dentistService = dentistService;
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<Appointment>>  findAll(){
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @PostMapping
    public ResponseEntity<Appointment> save(@RequestBody Appointment appointment){

        ResponseEntity<Appointment> response;


        //CHEQUEAMOS QUE EXISTAN  EL PACIENTE Y EL ODONTOLOGO
        if (dentistService.findById(appointment.getDentist().getId()) != null && patientService.findById(appointment.getPatient().getId()) != null) {

            //SETEAMOS AL RESPONSE ENTITY CON EL CODIGO 200 Y AGREGAMOS EL TURNO COMO CUERPO DE LA REPSUESTA
            response = ResponseEntity.ok(appointmentService.save(appointment));

        }else response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // SETEAR AL RESPONSE ENTITY EL CODIGO 400

        return response;

    }


}
