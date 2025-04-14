package com.dh.DentalClinicMVC.dao;

import com.dh.DentalClinicMVC.model.Appointment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AppointmentDaoList implements IDao<Appointment>{

    private  List<Appointment> appointments;

    public AppointmentDaoList(){
        appointments = new ArrayList<>();
    }

    @Override
    public Appointment save(Appointment appointment) {
        appointments.add(appointment);
        return appointment;
    }

    @Override
    public Appointment findById(Integer id) {
        Appointment appointmentToLookFor= null;
        for(Appointment a: appointments){
            if (a.getId()==id){
                appointmentToLookFor = a;
                return appointmentToLookFor;
            }
        }
        return appointmentToLookFor;
    }

    @Override
    public void update(Appointment appointment) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Appointment> findAll() {
        return appointments;
    }

    @Override
    public Appointment findByString(String value) {
        return null;
    }
}
