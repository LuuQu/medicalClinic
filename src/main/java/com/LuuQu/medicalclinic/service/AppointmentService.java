package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.mapper.AppointmentMapper;
import com.LuuQu.medicalclinic.model.dto.AppointmentDto;
import com.LuuQu.medicalclinic.model.entity.Appointment;
import com.LuuQu.medicalclinic.model.entity.Patient;
import com.LuuQu.medicalclinic.repository.AppointmentRepository;
import com.LuuQu.medicalclinic.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public AppointmentDto addPatient(Long patientId, Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        if (!appointment.isFree()) {
            throw new IllegalArgumentException("Appointment already have patient");
        }
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);
        return appointmentMapper.toDto(appointment);
    }

    @Transactional
    public AppointmentDto addAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = appointmentMapper.toEntity(appointmentDto);
        appointmentRepository.save(appointment);
        return appointmentDto;
    }
}
