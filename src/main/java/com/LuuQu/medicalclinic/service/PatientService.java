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

    public List<PatientDto> getPatients() {
        return patientRepository.getPatients()
                .stream()
                .map(PatientMapper::mapPatientEntityToDto)
                .toList();
    }

    public PatientDto getPatient(String email) {
        return PatientMapper.mapPatientEntityToDto(patientRepository.getPatient(email)
                                                    .orElseThrow(() -> new IllegalArgumentException("Brak pacjenta")));
    }

    public PatientDto addPatient(PatientDto patientDto) {
        Patient patient = PatientMapper.mapPatientDtoToEntity(patientDto);
        return PatientMapper.mapPatientEntityToDto(patientRepository.addPatient(patient)
                .orElseThrow(() -> new IllegalArgumentException("Pacjent o podanym e-mailu już istnieje")));
    }

    public void deletePatient(String email) {
        var patient = patientRepository.getPatient(email);
        if(patient.isEmpty()) {
            throw new IllegalArgumentException("Pacjent o podanym e-mailu nie istnieje");
        }
        patientRepository.deletePatient(patient.get());
    }

    public PatientDto editPatient(String email, PatientDto patientDto) {
        Patient patient = PatientMapper.mapPatientDtoToEntity(patientDto);
        return PatientMapper.mapPatientEntityToDto(patientRepository.editPatient(email, patient)
                .orElseThrow(() -> new IllegalArgumentException("Pacjent o podanym e-mailu nie istnieje")));
    }
    public PatientDto editPassword(String email, PatientDto patientPassword) {
        if(patientPassword.getPassword() == null) {
            throw new IllegalArgumentException("Podano niepoprawne body do zmiany hasła");
        }
        var patient = patientRepository.getPatient(email)
                .orElseThrow(() -> new IllegalArgumentException("Pacjent o podanym e-mailu nie istnieje"));
        patient.setPassword(patientPassword.getPassword());
        patientRepository.editPatient(email,patient);
        return PatientMapper.mapPatientEntityToDto(patient);
    }
}
