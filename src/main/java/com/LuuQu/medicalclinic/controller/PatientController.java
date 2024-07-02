package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.model.dto.PatientDto;
import com.LuuQu.medicalclinic.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/patients", produces = "application/json")
@Slf4j
public class PatientController {
    private final PatientService patientService;

    @Operation(summary = "Get list of patients by pages. \"page\": num of page, \"size\": num of elements in page.")
    @GetMapping
    public List<PatientDto> getPatientList(Pageable pageable) {
        log.info("getPatientList invoked with pageable = {}", pageable);
        return patientService.getPatients(pageable);
    }

    @Operation(summary = "Get single patient by id.")
    @GetMapping("/{id}")
    public PatientDto getPatient(@PathVariable Long id) {
        log.info("getPatient invoked with id = {}", id);
        return patientService.getPatient(id);
    }

    @Operation(summary = "Add patient to database. Patient description in request body.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto addPatient(@RequestBody PatientDto patient) {
        log.info("addPatient invoked with patient = {}", patient);
        return patientService.addPatient(patient);
    }

    @Operation(summary = "Delete patient by id.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        log.info("deletePatient invoked with id = {}", id);
        patientService.deletePatient(id);
    }

    @Operation(summary = "Edit patient with given id and replace data by request body.")
    @PutMapping("/{id}")
    public PatientDto editPatient(@PathVariable Long id, @RequestBody PatientDto patient) {
        log.info("editPatient invoked with id = {} and patient = {}", id, patient);
        return patientService.editPatient(id, patient);
    }

    @Operation(summary = "Edit password in request body to patient with given id.")
    @PatchMapping("/{id}/password")
    public PatientDto editPassword(@PathVariable Long id, @RequestBody PatientDto patient) {
        log.info("editPassword invoked with id = {} and patient = {}", id, patient);
        return patientService.editPassword(id, patient);
    }
}
