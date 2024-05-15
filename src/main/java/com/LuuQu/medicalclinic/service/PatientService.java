package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.model.Patient;
import com.LuuQu.medicalclinic.repository.PatientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public List<Patient> getPatients() {
        return patientRepository.getPatients();
    }

    public Patient getPatient(String email) {
        return patientRepository.getPatient(email)
                .orElseThrow(() -> new IllegalArgumentException("Brak pacjenta"));
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.addPatient(patient)
                .orElseThrow(() -> new IllegalArgumentException("Pacjent o podanym e-mailu już istnieje"));
    }

    public void deletePatient(String email) {
        var patient = patientRepository.getPatient(email);
        if(patient.isEmpty()) {
            throw new IllegalArgumentException("Pacjent o podanym e-mailu nie istnieje");
        }
        patientRepository.deletePatient(patient.get());
    }

    public Patient editPatient(String email, Patient patient) {
        return patientRepository.editPatient(email, patient)
                .orElseThrow(() -> new IllegalArgumentException("Pacjent o podanym e-mailu nie istnieje"));
    }
    public Patient editPassword(String email, Patient patientPassword) {
        if(patientPassword.getPassword() == null) {
            throw new IllegalArgumentException("Podano niepoprawne body do zmiany hasła");
        }
        var patient = patientRepository.getPatient(email)
                .orElseThrow(() -> new IllegalArgumentException("Pacjent o podanym e-mailu nie istnieje"));
        patient.setPassword(patientPassword.getPassword());
        patientRepository.editPatient(email,patient);
        return patient;
    }
}
