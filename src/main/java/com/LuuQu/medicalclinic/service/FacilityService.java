package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.exception.NotFoundException;
import com.LuuQu.medicalclinic.mapper.FacilityMapper;
import com.LuuQu.medicalclinic.model.dto.FacilityDto;
import com.LuuQu.medicalclinic.model.entity.Facility;
import com.LuuQu.medicalclinic.repository.FacilityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final FacilityMapper facilityMapper;

    public List<FacilityDto> getFacilities(Pageable pageable) {
        return facilityRepository.findAll(pageable).stream()
                .map(facilityMapper::toDto)
                .toList();
    }

    public FacilityDto getFacility(Long id) {
        return facilityMapper.toDto(facilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Non-existent facility")));
    }

    @Transactional
    public FacilityDto addFacility(FacilityDto facilityDto) {
        Facility facility = facilityMapper.toEntity(facilityDto);
        facilityRepository.save(facility);
        log.info("Facility {} added to database", facilityMapper.toDto(facility));
        return facilityMapper.toDto(facility);
    }

    @Transactional
    public FacilityDto editFacility(Long id, FacilityDto facilityDto) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Non-existent facility"));
        facility.update(facilityMapper.toEntity(facilityDto));
        facilityRepository.save(facility);
        log.info("Facility {} updated", facilityMapper.toDto(facility));
        return facilityMapper.toDto(facility);
    }

    @Transactional
    public void deleteFacility(Long id) {
        var facility = facilityRepository.findById(id);
        if (facility.isEmpty()) {
            throw new NotFoundException("Facility not found");
        }
        facilityRepository.delete(facility.get());
        log.info("Facility {} deleted", facilityMapper.toDto(facility.get()));
    }
}
