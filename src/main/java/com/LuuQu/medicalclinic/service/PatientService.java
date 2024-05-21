package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.mapper.PatientMapper;
import com.LuuQu.medicalclinic.model.dto.PatientDto;
import com.LuuQu.medicalclinic.model.entity.Patient;
import com.LuuQu.medicalclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDto> getPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::mapPatientEntityToDto)
                .toList();
    }

    public PatientDto getPatient(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Brak pacjenta"));
        return patientMapper.mapPatientEntityToDto(patient);
    }

    public PatientDto addPatient(PatientDto patientDto) {
        patientRepository.findByEmail(patientDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Pacjent o podanym e-mailu już istnieje"));
        Patient patient = patientMapper.mapPatientDtoToEntity(patientDto);
        patientRepository.save(patient);
        return patientDto;
    }

    public void deletePatient(String email) {
        patientRepository.delete(patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Pacjent o podanym e-mailu już istnieje")));
    }

    public PatientDto editPatient(String email, PatientDto patientDto) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Pacjent o podanym e-mailu nie istnieje"));
        patient.updatePatient(patientMapper.mapPatientDtoToEntity(patientDto));
        patientRepository.save(patient);
        return patientMapper.mapPatientEntityToDto(patient);
    }

    public PatientDto editPassword(String email, PatientDto patientPassword) {
        if (patientPassword.getPassword() == null) {
            throw new IllegalArgumentException("Podano niepoprawne body do zmiany hasła");
        }
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Pacjent o podanym e-mailu nie istnieje"));
        if (patient == null) {
            throw new IllegalArgumentException("Pacjent o podanym e-mailu nie istnieje");
        }
        patient.setPassword(patientPassword.getPassword());
        patientRepository.save(patient);
        return patientMapper.mapPatientEntityToDto(patient);
    }
}
