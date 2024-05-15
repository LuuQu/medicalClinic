package com.LuuQu.medicalclinic.repository;

import com.LuuQu.medicalclinic.model.entity.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PatientRepository {
    private final List<Patient> patientList;

    public List<Patient> getPatients() {
        return patientList.stream()
                .map(Patient::new)
                .collect(Collectors.toList());
    }

    public Optional<Patient> getPatient(String email) {
        return patientList.stream()
                .filter(item -> item.getEmail().equals(email))
                .findFirst();
    }

    public Optional<Patient> addPatient(Patient patient) {
        Optional<Patient> patientToAdd = patientList.stream()
                .filter(item -> item.getEmail().equals(patient.getEmail()))
                .findFirst();
        if (patientToAdd.isPresent()) {
            return Optional.empty();
        }
        patientList.add(patient);
        return Optional.of(patient);
    }

    public void deletePatient(Patient patient) {
        patientList.remove(patient);
    }

    public Optional<Patient> editPatient(String email, Patient patient) {
        Optional<Patient> patientOptional = patientList.stream()
                .filter(item -> item.getEmail().equals(email))
                .findFirst();
        if (patientOptional.isEmpty()) {
            return Optional.empty();
        }
        Patient patientToEdit = patientOptional.get();
        patientToEdit.setEmail(patient.getEmail());
        patientToEdit.setPassword(patient.getPassword());
        patientToEdit.setIdCardNo(patient.getIdCardNo());
        patientToEdit.setFirstName(patient.getFirstName());
        patientToEdit.setLastName(patient.getLastName());
        patientToEdit.setPhoneNumber(patient.getPhoneNumber());
        patientToEdit.setBirthday(patient.getBirthday());
        return patientOptional;
    }
}
