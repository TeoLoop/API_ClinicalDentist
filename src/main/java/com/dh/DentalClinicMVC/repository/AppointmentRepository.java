package com.dh.DentalClinicMVC.repository;

import com.dh.DentalClinicMVC.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
}
