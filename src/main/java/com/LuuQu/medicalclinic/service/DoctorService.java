package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.exception.NotFoundException;
import com.LuuQu.medicalclinic.mapper.DoctorMapper;
import com.LuuQu.medicalclinic.model.dto.DoctorDto;
import com.LuuQu.medicalclinic.model.entity.Doctor;
import com.LuuQu.medicalclinic.model.entity.Facility;
import com.LuuQu.medicalclinic.repository.DoctorRepository;
import com.LuuQu.medicalclinic.repository.FacilityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final FacilityRepository facilityRepository;
    private final DoctorMapper doctorMapper;

    public List<DoctorDto> getDoctors(Pageable pageable) {
        return doctorRepository.findAll(setCorrectPageableSort(pageable)).stream()
                .map(doctorMapper::toDto)
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

    public DoctorDto getDoctor(Long id) {
        return doctorMapper.toDto(doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Non-existent doctor")));
    }

    @Transactional
    public DoctorDto addDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.toEntity(doctorDto);
        doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }

    @Transactional
    public DoctorDto editDoctor(Long id, DoctorDto doctorDto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Non-existent doctor"));
        doctor.update(doctorMapper.toEntity(doctorDto));
        doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }

    public void deleteDoctor(Long id) {
        var doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            throw new NotFoundException("Doctor not found");
        }
        doctorRepository.delete(doctor.get());
    }

    public DoctorDto addDoctorFacility(Long doctorId, Long facilityId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Non-existent doctor"));
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new NotFoundException("Non-existent facility"));
        doctor.getFacilities().add(facility);
        doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }

    public DoctorDto removeDoctorFacility(Long doctorId, Long facilityId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Non-existent doctor"));
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new NotFoundException("Non-existent facility"));
        doctor.getFacilities().remove(facility);
        doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }
}
