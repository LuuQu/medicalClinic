package com.LuuQu.medicalclinic.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private PatientDto patient;
    private DoctorSimpleDto doctor;
}
