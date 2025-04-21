package com.dh.DentalClinicMVC.repository;

import com.dh.DentalClinicMVC.entity.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DentistRepository extends JpaRepository<Dentist, Long> {

    //HQL (Hibernate) o JPQL (JPA)
    //@Query("SELECT d FROM Dentist d WHERE d.registration=?1 ")
    Optional<Dentist> findByRegistration(Integer registration);
}
