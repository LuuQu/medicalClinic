package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.model.dto.DoctorDto;
import com.LuuQu.medicalclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping
    public List<DoctorDto> getDoctorList(@RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size) {
        return doctorService.getDoctors(page, size);
    }

    @GetMapping("/{id}")
    public DoctorDto getDoctor(@PathVariable Long id) {
        return doctorService.getDoctor(id);
    }

    @PostMapping
    public DoctorDto addDoctor(@RequestBody DoctorDto doctorDto) {
        return doctorService.addDoctor(doctorDto);
    }

    @PatchMapping("/{id}")
    public DoctorDto updateDoctor(@PathVariable Long id, @RequestBody DoctorDto doctorDto) {
        return doctorService.editDoctor(id, doctorDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }

    @PostMapping("/{doctorId}/facilities/{facilityId}")
    public DoctorDto addDoctorFacility(@PathVariable Long doctorId, @PathVariable Long facilityId) {
        return doctorService.addDoctorFacility(doctorId, facilityId);
    }

    @DeleteMapping("/{doctorId}/facilities/{facilityId}")
    public DoctorDto removeDoctorFacility(@PathVariable Long doctorId, @PathVariable Long facilityId) {
        return doctorService.removeDoctorFacility(doctorId, facilityId);
    }
}
