package com.LuuQu.medicalclinic.model.dto;

import com.LuuQu.medicalclinic.model.entity.Patient;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {
    private Long id;
    private LocalDateTime date;
    private PatientDto patient;
}
