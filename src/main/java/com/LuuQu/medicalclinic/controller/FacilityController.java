package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.model.dto.FacilityDto;
import com.LuuQu.medicalclinic.service.FacilityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/facilities", produces = "application/json")
@Slf4j
public class FacilityController {
    private final FacilityService facilityService;

    @Operation(summary = "Get list of facilities by pages. \"page\": num of page, \"size\": num of elements in page.")
    @GetMapping
    public List<FacilityDto> getFacilities(Pageable pageable) {
        log.info("getFacilities invoked with pageable = {}", pageable);
        return facilityService.getFacilities(pageable);
    }

    @Operation(summary = "Get single facility by id.")
    @GetMapping("/{id}")
    public FacilityDto getFacility(@PathVariable Long id) {
        log.info("getFacility invoked with id = {}", id);
        return facilityService.getFacility(id);
    }

    @Operation(summary = "Add facility to database. Facility description in request body.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityDto addFacility(@RequestBody FacilityDto facilityDto) {
        log.info("addFacility invoked with facilityDto = {}", facilityDto);
        return facilityService.addFacility(facilityDto);
    }

    @Operation(summary = "Edit facility with given id and replace data by request body.")
    @PatchMapping("/{id}")
    public FacilityDto updateFacility(@PathVariable Long id, @RequestBody FacilityDto facilityDto) {
        log.info("updateFacility invoked with id = {} and facilityDto = {}", id, facilityDto);
        return facilityService.editFacility(id, facilityDto);
    }

    @Operation(summary = "Delete facility by id.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFacility(@PathVariable Long id) {
        log.info("deleteFacility invoked with id = {}", id);
        facilityService.deleteFacility(id);
    }
}
