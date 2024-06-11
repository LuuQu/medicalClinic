package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.exception.NotFoundException;
import com.LuuQu.medicalclinic.exception.PatientException;
import com.LuuQu.medicalclinic.mapper.PatientMapper;
import com.LuuQu.medicalclinic.model.dto.PatientDto;
import com.LuuQu.medicalclinic.model.entity.Patient;
import com.LuuQu.medicalclinic.repository.PatientRepository;
import com.LuuQu.medicalclinic.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final PatientMapper patientMapper;

    public List<PatientDto> getPatients(Pageable pageable) {
        return patientRepository.findAll(setCorrectPageableSort(pageable)).stream()
                .map(patientMapper::toDto)
                .toList();
    }

    private Pageable setCorrectPageableSort(Pageable pageable) {
        List<Sort.Order> orders = new ArrayList<>();
        for (Sort.Order item : pageable.getSort().get().toList()) {
            String property = item.getProperty();
            if (property.contains("email")) {
                property = property.replaceAll("email", "user.email");
            }
            else if (property.contains("password")) {
                property = property.replaceAll("password", "user.password");
            }
            else if (property.contains("firstName")) {
                property = property.replaceAll("firstName", "user.firstName");
            }
            else if (property.contains("lastName")) {
                property = property.replaceAll("lastName", "user.lastName");
            }
            orders.add(new Sort.Order(item.getDirection(), property));
        }
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }

    public PatientDto getPatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient does not exist"));
        return patientMapper.toDto(patient);
    }

    @Transactional
    public PatientDto addPatient(PatientDto patientDto) {
        if (userRepository.findByEmail(patientDto.getEmail()).isPresent()) {
            throw new PatientException("User with given e-mail already exist");
        }
        Patient patient = patientMapper.toEntity(patientDto);
        patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    public void deletePatient(Long id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isEmpty()) {
            throw new NotFoundException("Patient not found");
        }
        patientRepository.delete(patientOptional.get());
    }

    @Transactional
    public PatientDto editPatient(Long id, PatientDto patientDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient does not exist"));
        patient.update(patientMapper.toEntity(patientDto));
        patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Transactional
    public PatientDto editPassword(Long id, PatientDto patientPassword) {
        if (patientPassword.getPassword() == null) {
            throw new PatientException("Incorrect body");
        }
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient does not exist"));
        patient.getUser().setPassword(patientPassword.getPassword());
        patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }
}
