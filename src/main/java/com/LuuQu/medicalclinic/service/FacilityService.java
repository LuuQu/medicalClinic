package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.mapper.FacilityMapper;
import com.LuuQu.medicalclinic.model.dto.FacilityDto;
import com.LuuQu.medicalclinic.model.entity.Facility;
import com.LuuQu.medicalclinic.repository.FacilityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final FacilityMapper facilityMapper;

    public List<FacilityDto> getFacilities() {
        return facilityRepository.findAll().stream()
                .map(facilityMapper::toDto)
                .toList();
    }

    public FacilityDto getFacility(Long id) {
        return facilityMapper.toDto(facilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Non-existent facility")));
    }

    @Transactional
    public FacilityDto addFacility(FacilityDto facilityDto) {
        Facility facility = facilityMapper.toEntity(facilityDto);
        return facilityMapper.toDto(facilityRepository.save(facility));
    }

    @Transactional
    public FacilityDto editFacility(Long id, FacilityDto facilityDto) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Non-existent facility"));
        facility.update(facilityMapper.toEntity(facilityDto));
        return facilityMapper.toDto(facilityRepository.save(facility));
    }

    @Transactional
    public void deleteFacility(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Non-existent facility"));
        facilityRepository.delete(facility);
    }
}
