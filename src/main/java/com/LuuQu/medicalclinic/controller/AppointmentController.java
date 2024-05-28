package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.model.dto.AppointmentDto;
import com.LuuQu.medicalclinic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PatchMapping("/{appointmentId}/patients/{patientId}")
    public AppointmentDto addPatient(@PathVariable Long patientId, @PathVariable Long appointmentId) {
        return appointmentService.addPatient(patientId, appointmentId);
    }

    @PostMapping
    public AppointmentDto addAppointment(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.addAppointment(appointmentDto);
    }
}
