package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.model.dto.PatientDto;
import com.LuuQu.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientDto> getPatientList(Pageable pageable) {
        return patientService.getPatients(pageable);
    }

    @GetMapping("/{id}")
    public PatientDto getPatient(@PathVariable Long id) {
        return patientService.getPatient(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto addPatient(@RequestBody PatientDto patient) {
        return patientService.addPatient(patient);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }

    @PutMapping("/{id}")
    public PatientDto editPatient(@PathVariable Long id, @RequestBody PatientDto patient) {
        return patientService.editPatient(id, patient);
    }

    @PatchMapping("/{id}/password")
    public PatientDto editPassword(@PathVariable Long id, @RequestBody PatientDto patient) {
        return patientService.editPassword(id, patient);
    }
}
