package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.model.dto.AppointmentDto;
import com.LuuQu.medicalclinic.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/appointments", produces = "application/json")
@Slf4j
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Operation(summary = "Connect appointment with patient by patient id and appointment id.")
    @PutMapping("/{appointmentId}/patients/{patientId}")
    public AppointmentDto addPatient(@PathVariable Long patientId, @PathVariable Long appointmentId) {
        log.info("addPatient invoked with patientId = {} and appointmentId = {}", patientId, appointmentId);
        return appointmentService.addPatient(patientId, appointmentId);
    }

    @Operation(summary = "Create new appointment by data in request body and connect appointment with doctor by doctor id.")
    @PostMapping("/{doctorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDto addAppointment(@PathVariable Long doctorId, @RequestBody AppointmentDto appointmentDto) {
        log.info("addAppointment invoked with doctorId = {} and appointmentDto = {}", doctorId, appointmentDto);
        return appointmentService.addAppointment(doctorId, appointmentDto);
    }

    @Operation(summary = "Return list of appointments that patient with given patientId is assigned to.")
    @GetMapping("/patient/{patientId}")
    public List<AppointmentDto> getPatientAppointments(@PathVariable Long patientId) {
        log.info("getPatientAppointments invoked with patientId = {}", patientId);
        return appointmentService.getPatientAppointments(patientId);
    }

    @Operation(summary = "Return list of appointments that doctor with given doctorId is assigned to.")
    @GetMapping("/doctor/{doctorId}")
    public List<AppointmentDto> getDoctorAppointments(@PathVariable Long doctorId) {
        log.info("getDoctorAppointments invoked with doctorId = {}", doctorId);
        return appointmentService.getDoctorAppointments(doctorId);
    }

    @Operation(summary = "Get all doctor appointments by specialization and day." +
            " Information about specialization, day and doctorId are stored in RequestParams")
    @GetMapping("/doctor")
    public List<AppointmentDto> getDoctorAvailableHours(@RequestParam LocalDate date,
                                                        @RequestParam String specialization,
                                                        @RequestParam Long doctorId) {
        log.info("getDoctorAppointments invoked with date = {}, specialization = {} and doctorId = {}", date, specialization, doctorId);
        return appointmentService.getDoctorAvailableHours(date, specialization, doctorId);
    }
}
