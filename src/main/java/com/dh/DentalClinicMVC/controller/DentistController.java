package com.dh.DentalClinicMVC.controller;

import com.dh.DentalClinicMVC.entity.Dentist;
import com.dh.DentalClinicMVC.exception.ResourceNotFoundException;
import com.dh.DentalClinicMVC.service.IDentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class DentistController {

    private IDentistService iDentistService;

    @Autowired
    public DentistController(IDentistService dentistService) {
        this.iDentistService = dentistService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Dentist> findById(@PathVariable Long id){
        Optional<Dentist> dentist = iDentistService.findById(id);

        if (dentist.isPresent()){
            return ResponseEntity.ok(dentist.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    //guardar odontologo
    @PostMapping
    public ResponseEntity<Dentist> save(@RequestBody Dentist dentist){
        return ResponseEntity.ok(iDentistService.save(dentist));
    }

    //actualizar odontolog
    @PutMapping
    public ResponseEntity<String> update(@RequestBody Dentist dentist){
        ResponseEntity<String> response;
        Optional<Dentist> dentistToLookFor = iDentistService.findById(dentist.getId());

        if (dentistToLookFor.isPresent()){
            iDentistService.update(dentist);
            response = ResponseEntity.ok("Se actualizo el odontologo con nombre: " + dentist.getName());
        }else {
            response = ResponseEntity.ok().body("No se puede actualizar el odontologo");
        }

        return response;
    }

    //borrar odontologo
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        iDentistService.delete(id);
        return ResponseEntity.ok("Se elimino el odontologo con id: " + id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Dentist>>  findAll(){
        return ResponseEntity.ok(iDentistService.findAll());
    }

    @GetMapping("/registration/{registration}")
    public ResponseEntity<Dentist> findByRegistration(@PathVariable Integer registration) throws Exception {
        Optional<Dentist> dentist = iDentistService.findByRegistration(registration);
        if (dentist.isPresent()){
            return ResponseEntity.ok(dentist.get());
        }else {
            throw new Exception("No se encontro la matricula: "+ registration);
        }
    }

}
