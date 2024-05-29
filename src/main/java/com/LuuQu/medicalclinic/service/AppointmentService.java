package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.mapper.AppointmentMapper;
import com.LuuQu.medicalclinic.model.dto.AppointmentDto;
import com.LuuQu.medicalclinic.model.entity.Appointment;
import com.LuuQu.medicalclinic.model.entity.Doctor;
import com.LuuQu.medicalclinic.model.entity.Patient;
import com.LuuQu.medicalclinic.repository.AppointmentRepository;
import com.LuuQu.medicalclinic.repository.DoctorRepository;
import com.LuuQu.medicalclinic.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
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
    public AppointmentDto addAppointment(Long doctorId, AppointmentDto appointmentDto) {
        if (appointmentDto.getStartDate().getMinute() % 15 != 0) {
            throw new IllegalArgumentException("Invalid startDate time");
        }
        if (appointmentDto.getEndDate().getMinute() % 15 != 0) {
            throw new IllegalArgumentException("Invalid endDate time");
        }
        if (appointmentDto.getStartDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment should be after active date");
        }
        if (appointmentDto.getStartDate().isAfter(appointmentDto.getEndDate())) {
            throw new IllegalArgumentException("Start date should be before end date");
        }
        if (appointmentDto.getStartDate().isEqual(appointmentDto.getEndDate())) {
            throw new IllegalArgumentException("Start date and end date shouldn't be the same");
        }
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor doesn't exist"));
        if (!appointmentRepository.getBetweenTime(doctorId, appointmentDto.getStartDate(), appointmentDto.getEndDate()).isEmpty()) {
            throw new IllegalArgumentException("Doctor already have appointment on set time");
        }
        Appointment appointment = appointmentMapper.toEntity(appointmentDto);
        appointment.setDoctor(doctor);
        appointmentRepository.save(appointment);
        return appointmentMapper.toDto(appointment);
    }
}
