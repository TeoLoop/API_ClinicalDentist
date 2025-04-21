package com.dh.DentalClinicMVC.service.impl;

import com.dh.DentalClinicMVC.dto.AppointmentDTO;
import com.dh.DentalClinicMVC.entity.Appointment;
import com.dh.DentalClinicMVC.entity.Dentist;
import com.dh.DentalClinicMVC.entity.Patient;
import com.dh.DentalClinicMVC.exception.ResourceNotFoundException;
import com.dh.DentalClinicMVC.repository.AppointmentRepository;
import com.dh.DentalClinicMVC.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService implements IAppointmentService {

    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public AppointmentDTO save(AppointmentDTO appointmentDTO) {
        //mapear nuestras entidaddes como DTO a mano
        //instanciar una entidad de turno

        Appointment appointmentEntity = new Appointment();

        //instanciar un paciente
        Patient patientEntity = new Patient();
        patientEntity.setId(appointmentDTO.getPatient_id());

        //instanciar odontologo
        Dentist dentistEntity = new Dentist();
        dentistEntity.setId(appointmentDTO.getDentist_id());

        //seteamos el paciente y odontologo a nuestra entidad turno
        appointmentEntity.setPatient(patientEntity);
        appointmentEntity.setDentist(dentistEntity);

        //convertir string del turno DTO que es la fecha a localDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(appointmentDTO.getDate(), formatter);

        //setear fecha
        appointmentEntity.setDate(date);

        //persistir en la base de datos
        appointmentRepository.save(appointmentEntity);

        //vamos a trabajar con el DTO que debemos devolver
        //generar una instacia de turno DTO
        AppointmentDTO appointmentDTOToReturn = new AppointmentDTO();

        //le seteamos los datos de la entidad que persistimos
        appointmentDTOToReturn.setId(appointmentEntity.getId());
        appointmentDTOToReturn.setDate(appointmentEntity.getDate().toString());
        appointmentDTOToReturn.setDentist_id(appointmentEntity.getDentist().getId());
        appointmentDTOToReturn.setPatient_id(appointmentEntity.getPatient().getId());


        return appointmentDTOToReturn;
    }

    @Override
    public Optional<AppointmentDTO> findById(Long id) throws ResourceNotFoundException {
        //buscamos la entidad por id en la BD
        Optional<Appointment> appointmentToLookFor =  appointmentRepository.findById(id);

        //instanciamos el dto
        Optional<AppointmentDTO> appointmentDTO = null;

        if (appointmentToLookFor.isPresent()){
            //Recuperar la entidad que se encontro y guardarla
            Appointment appointment = appointmentToLookFor.get();

            //trabajar sobre la info que tenemos que devolver: dto
            //creamos una instacioa de turno dto para devolver

            AppointmentDTO appointmentDTOToReturn = new AppointmentDTO(appointment.getId(), appointment.getDentist().getId(),
                    appointment.getPatient().getId(), appointment.getDate().toString());

            //lo convierto a un optional
            appointmentDTO = Optional.of(appointmentDTOToReturn);
            return appointmentDTO;
        }else {
            throw new ResourceNotFoundException("No se encontro el turno con id " + id);
        }

    }

    @Override
    public AppointmentDTO update(AppointmentDTO appointmentDTO) throws Exception {
        //chequeo que el turno a actualizar exista
        if (appointmentRepository.findById(appointmentDTO.getId()).isPresent()){

            //buscamos la entidad en la BD
            Optional<Appointment> appointmentEntity = appointmentRepository.findById(appointmentDTO.getId());

            //guardamos las cosas nuevas

            //instanciar un paciente
            Patient patientEntity = new Patient();
            patientEntity.setId(appointmentDTO.getPatient_id());

            //instanciar odontologo
            Dentist dentistEntity = new Dentist();
            dentistEntity.setId(appointmentDTO.getDentist_id());

            //seteamos el paciente y odontologo a nuestra entidad turno
            appointmentEntity.get().setPatient(patientEntity);
            appointmentEntity.get().setDentist(dentistEntity);

            //convertir string del turno DTO que es la fecha a localDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(appointmentDTO.getDate(), formatter);

            //setear fecha
            appointmentEntity.get().setDate(date);

            //persistir en la base de datos
            appointmentRepository.save(appointmentEntity.get());

            //trabajar sobre la respuesta (DTO) a devolver
            AppointmentDTO appointmentDTOToReturn = new AppointmentDTO();
            appointmentDTOToReturn.setId(appointmentEntity.get().getId());
            appointmentDTOToReturn.setDentist_id(appointmentEntity.get().getDentist().getId());
            appointmentDTOToReturn.setPatient_id(appointmentEntity.get().getPatient().getId());
            appointmentDTOToReturn.setDate(appointmentEntity.get().getDate().toString());

            return appointmentDTOToReturn;
        }else {
            throw new Exception("No se pudo actualizar el turno");
        }

    }

    @Override
    public Optional<AppointmentDTO> delete(Long id) throws ResourceNotFoundException {

        //Vamos a buscar la entidad por ID en la BD y guardarla en un Optional
        Optional<Appointment> appointmentToLookFor = appointmentRepository.findById(id);

        Optional<AppointmentDTO> appointmentDTO;

        if (appointmentToLookFor.isPresent()){
            //Recuperar el turno que se encontro y lo vamos a guardar en una variable turno
            Appointment appointment = appointmentToLookFor.get();

            //vamos a devolver un DTO
            //Vamos a trabajar sobre ese DTO a devoler
            //Crear instancia de ese DTO

            AppointmentDTO appointmentDTOToReturn = new AppointmentDTO();
            appointmentDTOToReturn.setId(appointment.getId());
            appointmentDTOToReturn.setDentist_id(appointment.getDentist().getId());
            appointmentDTOToReturn.setPatient_id(appointment.getPatient().getId());
            appointmentDTOToReturn.setDate(appointment.getDate().toString());

            appointmentDTO = Optional.of(appointmentDTOToReturn);
            return appointmentDTO;
        }else {
            //Vamos a lanzar la exception
            throw new ResourceNotFoundException("No se econtro el turno con ID: " + id);
        }
    }

    @Override
    public List<AppointmentDTO> findAll() {
        //vamos a traernos las entidades de la BD y la vamos a guardar en una lista
        List<Appointment> appointments = appointmentRepository.findAll();

        //vamos a crear una lista vacia de turnos DTO
        List<AppointmentDTO> appointmentsDTO = new ArrayList<>();

        //recorremos la lista de entidaddes de turno para luego guardarlas en la lista de turnos DTO
        for(Appointment appointment: appointments){
            appointmentsDTO.add(new AppointmentDTO(appointment.getId(),appointment.getDentist().getId(),appointment.getPatient().getId(),appointment.getDate().toString()));
        }

        return appointmentsDTO;
    }
}
