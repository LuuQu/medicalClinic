package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.model.dto.DoctorDto;
import com.LuuQu.medicalclinic.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/doctors", produces = "application/json")
@Slf4j
public class DoctorController {
    private final DoctorService doctorService;

    @Operation(summary = "Get list of doctors by pages. \"page\": num of page, \"size\": num of elements in page.")
    @GetMapping
    public List<DoctorDto> getDoctorList(Pageable pageable) {
        log.info("getDoctorList invoked with pageable = {}", pageable);
        return doctorService.getDoctors(pageable);
    }

    @Operation(summary = "Get single doctor by id.")
    @GetMapping("/{id}")
    public DoctorDto getDoctor(@PathVariable Long id) {
        log.info("getDoctor invoked with id = {}", id);
        return doctorService.getDoctor(id);
    }

    @Operation(summary = "Add doctor to database. Doctor description in request body.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto addDoctor(@RequestBody DoctorDto doctorDto) {
        log.info("addDoctor invoked with doctorDto = {}", doctorDto);
        return doctorService.addDoctor(doctorDto);
    }

    @Operation(summary = "Edit doctor with given id and replace data by request body.")
    @PatchMapping("/{id}")
    public DoctorDto updateDoctor(@PathVariable Long id, @RequestBody DoctorDto doctorDto) {
        log.info("updateDoctor invoked with id = {} and doctorDto = {}", id, doctorDto);
        return doctorService.editDoctor(id, doctorDto);
    }

    @Operation(summary = "Delete doctor by id.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable Long id) {
        log.info("deleteDoctor invoked with id = {}", id);
        doctorService.deleteDoctor(id);
    }

    @Operation(summary = "Add facility to doctor by facility id and doctor id.")
    @PostMapping("/{doctorId}/facilities/{facilityId}")
    public DoctorDto addDoctorFacility(@PathVariable Long doctorId, @PathVariable Long facilityId) {
        log.info("addDoctorFacility invoked with doctorId = {} and facilityId = {}", doctorId, facilityId);
        return doctorService.addDoctorFacility(doctorId, facilityId);
    }

    @Operation(summary = "Remove facility from doctor by facility id and doctor id.")
    @DeleteMapping("/{doctorId}/facilities/{facilityId}")
    public DoctorDto removeDoctorFacility(@PathVariable Long doctorId, @PathVariable Long facilityId) {
        log.info("removeDoctorFacility invoked with doctorId = {} and facilityId = {}", doctorId, facilityId);
        return doctorService.removeDoctorFacility(doctorId, facilityId);
    }
}
