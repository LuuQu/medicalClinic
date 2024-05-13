package com.LuuQu.medicalclinic.repository;

import com.LuuQu.medicalclinic.model.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PatientRepository {
    private List<Patient> patientList;

    public List<Patient> getPatientList() {
        return patientList;
    }

    public Optional<Patient> getPatient(String email) {
        return patientList.stream()
                .filter(item -> item.getEmail().equals(email))
                .findFirst();
    }

    public Optional<Patient> addPatient(Patient patient) {
        Optional<Patient> patientToAdd = patientList.stream().filter(item -> item.getEmail().equals(patient.getEmail())).findFirst();
        if (patientToAdd.isPresent()) {
            return Optional.empty();
        }
        patientList.add(patient);
        return patientList.stream().filter(item -> item.getEmail().equals(patient.getEmail())).findFirst();
    }

    public boolean deletePatient(String email) {
        Optional<Patient> patientToDelete = patientList.stream().filter(item -> item.getEmail().equals(email)).findFirst();
        if (patientToDelete.isEmpty()) {
            return false;
        }
        patientList.remove(patientToDelete.get());
        return true;
    }

    public Optional<Patient> editPatient(Patient patient) {
        Optional<Patient> patientOptional = patientList.stream().filter(item -> item.getEmail().equals(patient.getEmail())).findFirst();
        if (patientOptional.isEmpty()) {
            return Optional.empty();
        }
        Patient patientToEdit = patientOptional.get();
        patientToEdit.setPassword(patient.getPassword());
        patientToEdit.setIdCardNo(patient.getIdCardNo());
        patientToEdit.setFirstName(patient.getFirstName());
        patientToEdit.setLastName(patient.getLastName());
        patientToEdit.setPhoneNumber(patient.getPhoneNumber());
        patientToEdit.setBirthday(patient.getBirthday());
        return patientOptional;
    }
}
