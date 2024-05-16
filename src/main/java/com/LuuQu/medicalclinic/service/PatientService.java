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
        return patientRepository.getPatients()
                .stream()
                .map(patientMapper::mapPatientEntityToDto)
                .toList();
    }

    public PatientDto getPatient(String email) {
        return patientMapper.mapPatientEntityToDto(patientRepository.getPatient(email)
                                                    .orElseThrow(() -> new IllegalArgumentException("Brak pacjenta")));
    }

    public PatientDto addPatient(PatientDto patientDto) {
        Patient patient = patientMapper.mapPatientDtoToEntity(patientDto);
        return patientMapper.mapPatientEntityToDto(patientRepository.addPatient(patient)
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
        Patient patient = patientMapper.mapPatientDtoToEntity(patientDto);
        return patientMapper.mapPatientEntityToDto(patientRepository.editPatient(email, patient)
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
        return patientMapper.mapPatientEntityToDto(patient);
    }
}
