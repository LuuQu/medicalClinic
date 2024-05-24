package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.mapper.PatientMapper;
import com.LuuQu.medicalclinic.model.dto.PatientDto;
import com.LuuQu.medicalclinic.model.entity.Patient;
import com.LuuQu.medicalclinic.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDto> getPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .toList();
    }

    public PatientDto getPatient(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient does not exist"));
        return patientMapper.toDto(patient);
    }

    @Transactional
    public PatientDto addPatient(PatientDto patientDto) {
        if (patientRepository.findByEmail(patientDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Patient with given e-mail already exist");
        }
        Patient patient = patientMapper.toEntity(patientDto);
        patientRepository.save(patient);
        return patientDto;
    }

    public void deletePatient(String email) {
        patientRepository.delete(patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient does not exist")));
    }

    @Transactional
    public PatientDto editPatient(String email, PatientDto patientDto) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient does not exist"));
        patient.update(patientMapper.toEntity(patientDto));
        patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Transactional
    public PatientDto editPassword(String email, PatientDto patientPassword) {
        if (patientPassword.getPassword() == null) {
            throw new IllegalArgumentException("Incorrect body");
        }
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient does not exist"));
        patient.setPassword(patientPassword.getPassword());
        patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }
}
