package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.model.dto.AppointmentDto;
import com.LuuQu.medicalclinic.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/appointments", produces = "application/json")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Operation(summary = "Connect appointment with patient by patient id and appointment id.")
    @PatchMapping("/{appointmentId}/patients/{patientId}")
    public AppointmentDto addPatient(@PathVariable Long patientId, @PathVariable Long appointmentId) {
        return appointmentService.addPatient(patientId, appointmentId);
    }

    @Operation(summary = "Create new appointment by data in request body and connect appointment with doctor by doctor id.")
    @PostMapping("/{doctorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDto addAppointment(@PathVariable Long doctorId, @RequestBody AppointmentDto appointmentDto) {
        return appointmentService.addAppointment(doctorId, appointmentDto);
    }
}
