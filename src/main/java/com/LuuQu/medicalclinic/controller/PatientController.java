package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.model.Patient;
import com.LuuQu.medicalclinic.service.PatientService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<Patient> getPatientList() {
        return patientService.getPatients();
    }

    @GetMapping("/{email}")
    public Patient getPatient(@PathVariable String email) {
        return patientService.getPatient(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{email}")
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatient(email);
    }

    @PutMapping("/{email}")
    public Patient editPatient(@PathVariable String email, @RequestBody Patient patient) {
        return patientService.editPatient(email, patient);
    }
    @PatchMapping("change_password/{email}")
    public Patient editPassword(@PathVariable String email, @RequestBody String password) {
        return patientService.editPassword(email,password);
    }
}
