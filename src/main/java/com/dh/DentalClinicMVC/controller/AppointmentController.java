package com.dh.DentalClinicMVC.controller;

import com.dh.DentalClinicMVC.model.Appointment;
import com.dh.DentalClinicMVC.service.IAppointmentService;
import com.dh.DentalClinicMVC.service.IDentistService;
import com.dh.DentalClinicMVC.service.IPatientService;
import com.dh.DentalClinicMVC.service.impl.AppointmentService;
import com.dh.DentalClinicMVC.service.impl.DentistServiceImpl;
import com.dh.DentalClinicMVC.service.impl.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class AppointmentController {

    private IAppointmentService appointmentService;
    private IDentistService dentistService;
    private IPatientService patientService;

    @Autowired
    public AppointmentController(IAppointmentService appointmentService, IDentistService dentistService, IPatientService patientService) {
        this.appointmentService = appointmentService;
        this.dentistService = dentistService;
        this.patientService = patientService;
    }


    //CONSULTA TODOS LOS TURNOS
    @GetMapping
    public ResponseEntity<List<Appointment>>  findAll(){
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @PostMapping
    public ResponseEntity<Appointment> save(@RequestBody Appointment appointment){

        ResponseEntity<Appointment> response;


        //CHEQUEAMOS QUE EXISTAN  EL PACIENTE Y EL ODONTOLOGO
        if (dentistService.findById(appointment.getDentist().getId()).isPresent()
                && patientService.findById(appointment.getPatient().getId()).isPresent()) {

            //SETEAMOS AL RESPONSE ENTITY CON EL CODIGO 200 Y AGREGAMOS EL TURNO COMO CUERPO DE LA REPSUESTA
            response = ResponseEntity.ok(appointmentService.save(appointment));

        }else response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // SETEAR AL RESPONSE ENTITY EL CODIGO 400

        return response;

    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> findById(@PathVariable Long id){
        Optional<Appointment> appointmentToLookFor = appointmentService.findById(id);

        if (appointmentToLookFor.isPresent()){
            return ResponseEntity.ok(appointmentToLookFor.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Appointment appointment){
        ResponseEntity<String> response;

        if (dentistService.findById(appointment.getDentist().getId()).isPresent()
                && patientService.findById(appointment.getPatient().getId()).isPresent()){

            appointmentService.update(appointment);
            response = ResponseEntity.ok("Se actualizo el turno con id: " + appointment.getId());
        }else {
            response = ResponseEntity.badRequest().body("No se encontro turno en la BD");
        }

        return response;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete (@PathVariable Long id){
        ResponseEntity<String> response;

        if (appointmentService.findById(id).isPresent()){
            appointmentService.delete(id);
            response = ResponseEntity.ok("Se elimino el turno con id: " + id);

        }else {
            response = ResponseEntity.ok().body("No se puede el iminar el turno, no se encontro en la BD");
        }

        return response;
    }


}
