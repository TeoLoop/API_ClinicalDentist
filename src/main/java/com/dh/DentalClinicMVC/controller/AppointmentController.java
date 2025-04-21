package com.dh.DentalClinicMVC.controller;

import com.dh.DentalClinicMVC.dto.AppointmentDTO;
import com.dh.DentalClinicMVC.entity.Appointment;
import com.dh.DentalClinicMVC.exception.ResourceNotFoundException;
import com.dh.DentalClinicMVC.service.IAppointmentService;
import com.dh.DentalClinicMVC.service.IDentistService;
import com.dh.DentalClinicMVC.service.IPatientService;
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
    public ResponseEntity<List<AppointmentDTO>>  findAll(){
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> save(@RequestBody AppointmentDTO appointmentDTO){

        ResponseEntity<AppointmentDTO> response;


        //CHEQUEAMOS QUE EXISTAN  EL PACIENTE Y EL ODONTOLOGO
        if (dentistService.findById(appointmentDTO.getDentist_id()).isPresent()
                && patientService.findById(appointmentDTO.getPatient_id()).isPresent()) {

            //SETEAMOS AL RESPONSE ENTITY CON EL CODIGO 200 Y AGREGAMOS EL TURNO COMO CUERPO DE LA REPSUESTA
            response = ResponseEntity.ok(appointmentService.save(appointmentDTO));

        }else response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // SETEAR AL RESPONSE ENTITY EL CODIGO 400

        return response;

    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<AppointmentDTO> appointmentToLookFor = appointmentService.findById(id);

        if (appointmentToLookFor.isPresent()){
            return ResponseEntity.ok(appointmentToLookFor.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<AppointmentDTO> update(@RequestBody AppointmentDTO appointmentDTO) throws Exception{
        ResponseEntity<AppointmentDTO> response;

        if (dentistService.findById(appointmentDTO.getDentist_id()).isPresent()
                && patientService.findById(appointmentDTO.getPatient_id()).isPresent()){
            //seteamos el responseentity con el cod 200 y le agregamos el turno dto como cuerpo de la respuesta
            response = ResponseEntity.ok(appointmentService.update(appointmentDTO));
        }else {
            response = ResponseEntity.badRequest().build();
        }

        return response;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete (@PathVariable Long id) throws ResourceNotFoundException {
        appointmentService.delete(id);
        return ResponseEntity.ok("Se elimino el turno con ID: " + id);
    }


}
