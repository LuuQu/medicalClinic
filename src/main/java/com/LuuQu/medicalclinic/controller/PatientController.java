package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.model.Patient;
import com.LuuQu.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;

    public List<Patient> getPatientList() {
        return patientService.getPatientList();
    }

    @GetMapping("/{email}")
    public Patient getPatient(@PathVariable String email) {
        return patientService.getPatient(email);
    }

    @ResponseStatus(HttpStatus.CREATED)
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/{email}")
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatient(email);
    }

    @GetMapping("/{email}")
    public Patient editPatient(@PathVariable String email, @RequestBody Patient patient) {
        return patientService.editPatient(email, patient);
    }
}
