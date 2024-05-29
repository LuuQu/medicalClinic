package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.model.dto.FacilityDto;
import com.LuuQu.medicalclinic.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facilities")
public class FacilityController {
    private final FacilityService facilityService;

    @GetMapping
    public List<FacilityDto> getFacilities(Pageable pageable) {
        return facilityService.getFacilities(pageable);
    }

    @GetMapping("/{id}")
    public FacilityDto getFacility(@PathVariable Long id) {
        return facilityService.getFacility(id);
    }

    @PostMapping
    public FacilityDto addFacility(@RequestBody FacilityDto facilityDto) {
        return facilityService.addFacility(facilityDto);
    }

    @PatchMapping("/{id}")
    public FacilityDto updateFacility(@PathVariable Long id, @RequestBody FacilityDto facilityDto) {
        return facilityService.editFacility(id, facilityDto);
    }

    @DeleteMapping("/{id}")
    public void deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
    }
}
