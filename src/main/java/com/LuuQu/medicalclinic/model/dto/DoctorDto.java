package com.LuuQu.medicalclinic.model.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class DoctorDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String specialization;
    private Set<FacilitySimpleDto> facilities = new HashSet<>();
}
