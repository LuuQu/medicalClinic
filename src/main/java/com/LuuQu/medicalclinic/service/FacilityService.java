package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.mapper.FacilityMapper;
import com.LuuQu.medicalclinic.model.dto.FacilityDto;
import com.LuuQu.medicalclinic.model.entity.Doctor;
import com.LuuQu.medicalclinic.model.entity.Facility;
import com.LuuQu.medicalclinic.repository.DoctorRepository;
import com.LuuQu.medicalclinic.repository.FacilityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final DoctorRepository doctorRepository;
    private final FacilityMapper facilityMapper;

    public List<FacilityDto> getFacilities() {
        return facilityRepository.findAll().stream()
                .map(facilityMapper::toDto)
                .collect(Collectors.toList());
    }

    public FacilityDto getFacility(Long id) {
        return facilityMapper.toDto(facilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Non-existent facility")));
    }

    @Transactional
    public FacilityDto addFacility(FacilityDto facilityDto) {
        Facility facility = facilityMapper.toEntity(facilityDto);
        facilityRepository.save(facility);
        return facilityDto;
    }

    @Transactional
    public FacilityDto editFacility(Long id, FacilityDto facilityDto) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Non-existent facility"));
        facility.update(facilityMapper.toEntity(facilityDto));
        facilityRepository.save(facility);
        return facilityDto;
    }

    public void deleteFacility(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Non-existent facility"));
        facilityRepository.delete(facility);
    }
}
