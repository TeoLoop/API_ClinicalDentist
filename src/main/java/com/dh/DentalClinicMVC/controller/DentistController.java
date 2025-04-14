package com.dh.DentalClinicMVC.controller;

import com.dh.DentalClinicMVC.model.Dentist;
import com.dh.DentalClinicMVC.service.DentistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class DentistController {

    DentistService dentistService;

    public DentistController(DentistService dentistService) {
        this.dentistService = dentistService;
    }

    @GetMapping("/{id}")
    public Dentist findById(@PathVariable Integer id){
        return dentistService.findById(id);
    }

    //guardar odontologo
    @PostMapping
    public Dentist save(@RequestBody Dentist dentist){
        return dentistService.save(dentist);
    }

    //actualizar odontolog
    @PutMapping
    public void update(@RequestBody Dentist dentist){
        dentistService.update(dentist);
    }

    //borrar odontologo
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        dentistService.delete(id);
    }

    @GetMapping("/all")
    public List<Dentist> findAll(){
        return dentistService.findAll();
    }

}
