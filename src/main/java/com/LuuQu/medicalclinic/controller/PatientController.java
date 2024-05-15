package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.model.dto.PatientDto;
import com.LuuQu.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientDto> getPatientList() {
        return patientService.getPatients();
    }

    @GetMapping("/{email}")
    public PatientDto getPatient(@PathVariable String email) {
        return patientService.getPatient(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto addPatient(@RequestBody PatientDto patient) {
        return patientService.addPatient(patient);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{email}")
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatient(email);
    }

    @PutMapping("/{email}")
    public PatientDto editPatient(@PathVariable String email, @RequestBody PatientDto patient) {
        return patientService.editPatient(email, patient);
    }
    @PatchMapping("change_password/{email}")
    public PatientDto editPassword(@PathVariable String email, @RequestBody PatientDto patient) {
        return patientService.editPassword(email,patient);
    }
}
